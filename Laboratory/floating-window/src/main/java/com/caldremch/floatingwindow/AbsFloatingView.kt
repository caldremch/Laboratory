package com.caldremch.floatingwindow

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.core.view.GravityCompat
import kotlin.math.abs

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/1 15:06
 *
 * @description
 *
 *
 */
open abstract class AbsFloatingView : FloatingViewLifeCycle, TouchProxy.OnTouchEventListener {

    var status = FloatingViewEnum.UN_ATTACH
    var animPreStatus = FloatingViewAnimStatusEnum.NORMAL
    protected val TAG = "FloatingViewTAG"
    private var mChildView: View? = null
    private var mFloatingViewHeight = 0
    private var mFloatingViewWidth = 0
    private lateinit var mFloatingViewLayoutParams: FloatingViewLayoutParams
    protected var mWindowManager = AppViewManager.INSTANCE.windowManager


    /**
     * 用于获取View的宽高
     */
    private var mViewTreeObserver: ViewTreeObserver? = null
    private val mOnGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener =
        ViewTreeObserver.OnGlobalLayoutListener {
            //每次布局发生变动的时候重新赋值
            mRootView?.let {
                mFloatingViewHeight = it.measuredHeight
                mFloatingViewWidth = it.measuredWidth
//                Log.d(TAG, "悬浮View的宽高: $mFloatingViewWidth , $mFloatingViewHeight ")
                moveAboveStatusBarIfNeed(it)
            }
        }


    /**
     * 将悬浮内容移动到状态栏之外
     */
    private fun moveAboveStatusBarIfNeed(rootView: View) {
        if (animPreStatus != FloatingViewAnimStatusEnum.NORMAL) {
            return
        }
        animPreStatus = FloatingViewAnimStatusEnum.EXECUTING
        val params = systemLayoutParams!!
        if (rootView.visibility == View.INVISIBLE) {
            //改变view的x和y
            params.y -= mFloatingViewHeight
            mWindowManager.updateViewLayout(rootView, params)
            //移动完后, 设置为可见, 
            rootView.visibility = View.VISIBLE
            //执行一个2s的动画
            animShow(FloatingViewAnimShowCategoryEnum.TOP_BOTTOM, params)
        }
    }

    private fun animShow(
        showCategory: FloatingViewAnimShowCategoryEnum,
        params: WindowManager.LayoutParams
    ) {
        when (showCategory) {
            FloatingViewAnimShowCategoryEnum.TOP_BOTTOM -> {
                val animator = ValueAnimator.ofInt(params.y, 0).apply {
                    duration = 500
                    interpolator = DecelerateInterpolator()
                }
                animator.addUpdateListener {
                    params.y = it.animatedValue as Int
                    mWindowManager.updateViewLayout(mRootView!!, params)
                }
                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        animPreStatus = FloatingViewAnimStatusEnum.DONE
                    }
                })
                animator.start()
            }
        }
    }


    private fun addViewTreeObserverListener() {
        if (mViewTreeObserver == null && mRootView != null) {
            mViewTreeObserver = mRootView!!.viewTreeObserver
            mViewTreeObserver?.addOnGlobalLayoutListener(mOnGlobalLayoutListener)

        }
    }

    private fun removeViewTreeObserverListener() {
        mViewTreeObserver?.let {
            if (it.isAlive) {
                it.removeOnGlobalLayoutListener(mOnGlobalLayoutListener)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //一般不会执行destroy, 因为显示后, 就可以记录住这个宽高了, 现在的宽高并不会发生变动, 所以也不需要移除, 如果有需要移除, 再迭代
        removeViewTreeObserverListener()
    }

    override fun onCreateView(context: Context, rootView: FrameLayout?): View {
        return View(context)
    }

    /**
     * 获取屏幕短边的长度 不包含statusBar
     */
    val screenShortSideLength: Int
        get() = if (FloatingViewUtil.isPortrait()) {
            FloatingViewUtil.getAppScreenWidth()
        } else {
            FloatingViewUtil.getAppScreenHeight()
        }

    /**
     * 获取屏幕长边的长度 不包含statusBar
     */
    val screenLongSideLength: Int
        get() = if (FloatingViewUtil.isPortrait()) {
            FloatingViewUtil.getAppScreenHeight()
        } else {
            FloatingViewUtil.getAppScreenWidth()
        }

    val tagName: String
        get() {
            return this.javaClass.canonicalName ?: ""
        }


    var mRootView: FrameLayout? = null


    val realFloatingView: View?
        get() = mRootView

    var systemLayoutParams: WindowManager.LayoutParams? = null

    /**
     * 手势代理
     */
    @JvmField
    var mTouchProxy = TouchProxy(this)


    /**
     * 悬浮窗要动画的形式显示出来, 所以一开始不能直接显示, 而是先设置为不可见 INVISIBLE
     */


    /**
     * 执行floatPage create
     */
    fun performCreate(context: Context) {
        try {
            onCreate(context)
            mRootView = FloatingViewContainerLayout(context)//系统悬浮窗的返回按键监听
            mRootView!!.visibility = View.INVISIBLE //先设置为隐藏
            //添加根布局的layout回调
            addViewTreeObserverListener()

            //调用onCreateView抽象方法
            mChildView = onCreateView(context, mRootView)
            //将子View添加到rootview中
            mRootView!!.addView(mChildView)
            //设置根布局的手势拦截
            mRootView!!.setOnTouchListener { v, event -> mTouchProxy.onTouchEvent(v, event) }
            //调用onViewCreated回调
            onViewCreated(mRootView)
            mFloatingViewLayoutParams = FloatingViewLayoutParams()

            systemLayoutParams = WindowManager.LayoutParams()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //android 8.0
                systemLayoutParams?.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                systemLayoutParams?.type = WindowManager.LayoutParams.TYPE_PHONE
            }
            //shouldDealBackKey : fasle 不自己收返回事件处理
            //参考：http://www.shirlman.com/tec/20160426/362

            //设置WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE会导致RootView监听不到返回按键的监听失效 系统处理返回按键
            systemLayoutParams?.flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            mFloatingViewLayoutParams.flags =
                FloatingViewLayoutParams.FLAG_NOT_FOCUSABLE or FloatingViewLayoutParams.FLAG_LAYOUT_NO_LIMITS


            systemLayoutParams?.apply {
                format = PixelFormat.TRANSPARENT
                gravity = Gravity.START or Gravity.TOP
            }

            mFloatingViewLayoutParams.gravity = GravityCompat.START or Gravity.TOP
            initFloatingViewLayoutParams(mFloatingViewLayoutParams)
            systemLayoutParams?.let {
                onSystemLayoutParamsCreated()
            }
        } catch (e: Exception) {
            Log.e(TAG, "e===>" + e.message)
            e.printStackTrace()
        }
    }


    /**
     * 确定系统浮标的初始位置
     * LayoutParams创建完以后调用
     * 调用时建议放在实现下方
     *
     * @param params
     */
    private fun onSystemLayoutParamsCreated() {
        //如果有上一个页面的位置记录 这更新位置
        systemLayoutParams?.flags = mFloatingViewLayoutParams.flags
        systemLayoutParams?.gravity = mFloatingViewLayoutParams.gravity
        systemLayoutParams?.width = mFloatingViewLayoutParams.width
        systemLayoutParams?.height = mFloatingViewLayoutParams.height
        val floatingViewInfo: FloatingViewInfo? = null
        if (floatingViewInfo != null) {
            if (FloatingViewUtil.isPortrait()) {
                systemLayoutParams?.x = floatingViewInfo.portraitPoint.x
                systemLayoutParams?.y = floatingViewInfo.portraitPoint.y
            } else if (FloatingViewUtil.isLandscape()) {
                systemLayoutParams?.x = floatingViewInfo.landscapePoint.x
                systemLayoutParams?.y = floatingViewInfo.landscapePoint.y
            }
        } else {
            //初始化坐标, (0, 0)
            systemLayoutParams?.x = mFloatingViewLayoutParams.x
            systemLayoutParams?.y = mFloatingViewLayoutParams.y
        }

    }

    override fun onMove(x: Int, y: Int, dx: Int, dy: Int) {
        if (!canDrag()) {
            return
        }
        systemLayoutParams?.apply {
            this.x += dx
            this.y += dy
        }


        //限制布局边界
        resetBorderline(null, systemLayoutParams)
        //改变view的x和y
        val dismissDistance = getDismissYDistance()
        if (dismissDistance != null && abs(dy) > dismissDistance) {
            animDestroy(FloatingViewAnimDismissCategoryEnum.BTT, systemLayoutParams!!)
        } else {
            mWindowManager.updateViewLayout(mRootView, systemLayoutParams)
        }
    }

    private fun animDestroy(
        dismissCategoryEnum: FloatingViewAnimDismissCategoryEnum,
        params: WindowManager.LayoutParams
    ) {
        when (dismissCategoryEnum) {
            FloatingViewAnimDismissCategoryEnum.BTT -> {
                //要加上状态栏或者2倍 保证能完全消失即可
                val animator =
                    ValueAnimator.ofInt(0, (mFloatingViewHeight + mFloatingViewHeight)).apply {
                        duration = 500
                        interpolator = DecelerateInterpolator()
                    }
                animator.addUpdateListener {
                    val value = it.animatedValue as Int
                    params.y = -value
                    Log.d(TAG, "animDestroy: $value")
                    if (mRootView!!.isAttachedToWindow){
                        mWindowManager.updateViewLayout(mRootView!!, params)
                    }
                }
                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        animPreStatus = FloatingViewAnimStatusEnum.DONE
                        AppViewManager.INSTANCE.destroy(FloatingIntent(this@AbsFloatingView::class.java))
                    }
                })
                animator.start()
            }
        }
    }

    open fun getDismissAnimType(): FloatingViewAnimDismissCategoryEnum =
        FloatingViewAnimDismissCategoryEnum.BTT

    open fun getDismissYDistance(): Int? = 20

    /**
     * 限制边界 调用的时候必须保证是在控件能获取到宽高德前提下
     */
    private fun resetBorderline(
        normalFrameLayoutParams: FrameLayout.LayoutParams?,
        windowLayoutParams: WindowManager.LayoutParams?
    ) {
        //普通模

        if (windowLayoutParams != null) {
            if (FloatingViewUtil.isPortrait()) {
                if (windowLayoutParams.y >= screenLongSideLength - mFloatingViewHeight) {
                    windowLayoutParams.y = screenLongSideLength - mFloatingViewHeight
                }
            } else {
                if (windowLayoutParams.y >= screenShortSideLength - mFloatingViewHeight) {
                    windowLayoutParams.y = screenShortSideLength - mFloatingViewHeight
                }
            }

            if (FloatingViewUtil.isPortrait()) {
                if (windowLayoutParams.x >= screenShortSideLength - mFloatingViewWidth) {
                    windowLayoutParams.x = screenShortSideLength - mFloatingViewWidth
                }
            } else {
                if (windowLayoutParams.x >= screenLongSideLength - mFloatingViewWidth) {
                    windowLayoutParams.x = screenLongSideLength - mFloatingViewWidth
                }
            }

            //系统模式
            if (justFloating[0] && windowLayoutParams.y <= 0) {
                windowLayoutParams.y = 0
            }

            if (justFloating[1] && windowLayoutParams.x <= 0) {
                windowLayoutParams.x = 0
            }

            if (justFloating[2] && windowLayoutParams.x >= 0) {
                windowLayoutParams.x = 0
            }

            if (justFloating[3] && windowLayoutParams.y >= 0) {
                windowLayoutParams.y = 0
            }
        }

    }

    open val justFloating: Array<Boolean> = arrayOf(false, false, false, false)//仅仅悬浮, 不能

    /**
     * 手指弹起时保存当前浮标位置
     *
     * @param x
     * @param y
     */
    override fun onUp(x: Int, y: Int) {
        if (!canDrag()) {
            return
        }
//        if (!viewProps.edgePinned) {
//            endMoveAndRecord()
//            return
//        }
        animatedMoveToEdge()
    }


    private inline fun makeAnimator(
        from: Int,
        size: Int,
        containerSize: Int,
        setup: ValueAnimator.() -> Unit
    ) {
        if (size <= 0 || containerSize <= 0) return
        ValueAnimator.ofInt(
            from,
            if (from <= (containerSize - size) / 2) 0 else (containerSize - size)
        )
            .apply {
                duration = 150L
                setup()
            }
            .start()
    }


    private fun animatedMoveToEdge() {
        /*val viewSize = mRootView?.width ?: return
        systemLayoutParams?.also { layoutAttrs ->
            makeAnimator(layoutAttrs.x, viewSize, Env.windowSize.x) {
                addUpdateListener { v ->
                    layoutAttrs.x = v.animatedValue as Int
                    mWindowManager.updateViewLayout(mRootView, layoutAttrs)
                }
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        endMoveAndRecord()
                    }
                })
            }
        }*/
    }

    private fun endMoveAndRecord() {

    }

    /**
     * 手指按下时的操作
     *
     * @param x
     * @param y
     */
    override fun onDown(x: Int, y: Int) {
        if (!canDrag()) {
            return
        }
    }

    override fun onEnterBackground() {

    }

    override fun onEnterForeground() {

    }


}
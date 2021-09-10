package com.caldremch.laboratory.fragment.nestscroll

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/8/11 15:31
 *
 * @description  https://www.jianshu.com/p/e333f11f29aa
 *
 *
 */
class NestScrollChildView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), NestedScrollingChild {
    private var mLastX //手指在屏幕上最后的x位置
            = 0f
    private var mLastY //手指在屏幕上最后的y位置
            = 0f

    private var mDownX //手指第一次落下时的x位置（忽略）
            = 0f
    private var mDownY //手指第一次落下时的y位置
            = 0f

    private var consumed = IntArray(2) //消耗的距离

    private var offsetInWindow = IntArray(2)

    private val TAG = this.javaClass.simpleName

    private val nestedScrollingChildHelper = NestedScrollingChildHelper(this)

    init {
        //默认开启支持嵌套滑动
        nestedScrollingChildHelper.isNestedScrollingEnabled = true
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {

        val x: Float = ev.x
        val y: Float = ev.y

        val action: Int = ev.action

        when (action) {
            MotionEvent.ACTION_DOWN -> {

                mDownY = x
                mDownX = y

                startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL or ViewCompat.SCROLL_AXIS_VERTICAL)
            }
            MotionEvent.ACTION_MOVE -> {

                var dy = (y - mDownY)
                var dx = (x - mDownX)

                Log.d(TAG, "[$dx, $dy]")

                if (dispatchNestedPreScroll(dx.toInt(), dy.toInt(), consumed, offsetInWindow)) {
                    //减掉父类消耗的距离
                    dx -= consumed.get(0)
                    dy -= consumed.get(1)
                }

                offsetTopAndBottom(dy.toInt())
                offsetLeftAndRight(dx.toInt())
            }
            MotionEvent.ACTION_UP -> {
                stopNestedScroll()
            }
        }
        mLastX = x;
        mLastY = y;


        return true
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return nestedScrollingChildHelper.isNestedScrollingEnabled
    }

}
package com.caldremch.floatingwindow

import android.content.Context
import android.util.Log
import com.caldremch.floatingwindow.callback.InternalFloatingViewOnShow
import com.caldremch.floatingwindow.model.FloatViewBuffer

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/1 14:45
 *
 * @description app悬浮窗口管理
 *
 *
 */
class AppFloatingViewManagerDoubleBufferImpl : IFloatingViewManager {

    private val TAG = "FloatingViewTAG"
    private val mWindowManager = AppViewManager.INSTANCE.windowManager
    private val mContext: Context by lazy { Utils.context }
    private val buffer = FloatViewBuffer()
    private var dismissPreFloatingView = true

    override fun attachToRecover() {
        try {
            buffer.front?.let {
                //重新显示View
                mWindowManager.addView(
                    it.virtualView.realFloatingView,
                    it.virtualView.systemLayoutParams
                )
                it.virtualView.status = FloatingViewEnum.ATTACH
                //重新显示, 不需要更新信息(一般是从后台切换回来)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun attach(floatingIntent: FloatingIntent) {
        try {

            if (buffer.front != null) {
                //将当前窗口切换到back, 准备被销毁
                buffer.back = buffer.front
                buffer.front = null
            }

            val floatVirtualView = floatingIntent.targetClass.newInstance()
            if (dismissPreFloatingView) {
                floatVirtualView.floatingViewOnShow = object : InternalFloatingViewOnShow {
                    override fun onShow() {
                        //删除intent
                        Log.d(TAG, "onShow: 销毁${buffer.back}")
                        buffer.back?.let { destroy(it.intent) }
                    }
                }
            }
            floatVirtualView.performCreate(mContext)
            mWindowManager.addView(
                floatVirtualView.realFloatingView,
                floatVirtualView.systemLayoutParams
            )
            buffer.front = FloatViewBuffer.Node(floatingIntent, floatVirtualView)
            Log.d(TAG, "onShow: 展示${buffer.front}")
            floatVirtualView.status = FloatingViewEnum.ATTACH
            floatVirtualView.onResume()
            floatVirtualView.onUpdate(floatingIntent.bundle) //数据设置

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun detach() {
        buffer.front?.let {
            it.virtualView.status = FloatingViewEnum.DETACH
            mWindowManager.removeView(it.virtualView.realFloatingView) //移除
        }
    }


    override fun destroy(floatingIntent: FloatingIntent) {
        buffer.findByIntent(floatingIntent)?.let {
            if (it.virtualView.mRootView!!.isAttachedToWindow) {
                mWindowManager.removeView(it.virtualView.realFloatingView) //移除
            }
            it.virtualView.onDestroy()
        }
    }

}
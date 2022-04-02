package com.caldremch.floatingwindow

import android.content.Context
import android.util.Log

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
class AppFloatingViewManagerImpl : IFloatingViewManager {

    private val TAG = "FloatingViewTAG"
    private val mWindowManager = AppViewManager.INSTANCE.windowManager
    private val mContext: Context by lazy { Utils.context }
    private val mFloatingViews: MutableList<AbsFloatingView> by lazy { mutableListOf() }


    override fun attachToRecover() {
        try {
            for (floatingView in mFloatingViews) {
                //重新显示View
                mWindowManager.addView(
                    floatingView.realFloatingView,
                    floatingView.systemLayoutParams
                )
                floatingView.status = FloatingViewEnum.ATTACH
                //重新显示, 不需要更新信息(一般是从后台切换回来)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    override fun attach(floatingIntent: FloatingIntent) {
        try {

            //同类型只展示一个
//            for (floatingView in mFloatingViews) {
//                if (floatingIntent.targetClass.isInstance(floatingView)) {
//                    //更新信息
//                    floatingView.onUpdate(floatingIntent.bundle)
//                    return
//                }
//            }
            val floatVirtualView = floatingIntent.targetClass.newInstance()
            mFloatingViews.add(floatVirtualView)
            floatVirtualView.performCreate(mContext)
            mWindowManager.addView(
                floatVirtualView.realFloatingView,
                floatVirtualView.systemLayoutParams
            )
            Log.d(
                TAG,
                "attach: x=${floatVirtualView.systemLayoutParams?.x}, y =${floatVirtualView.systemLayoutParams?.y} "
            )
            floatVirtualView.status = FloatingViewEnum.ATTACH
            floatVirtualView.onResume()
            floatVirtualView.onUpdate(floatingIntent.bundle)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun detach() {
        val it = mFloatingViews.iterator()
        while (it.hasNext()) {
            val virtualFloatingView = it.next()
            virtualFloatingView.status = FloatingViewEnum.DETACH
            mWindowManager.removeView(virtualFloatingView.realFloatingView) //移除
        }
    }


    override fun destroy(floatingIntent: FloatingIntent) {
        val it = mFloatingViews.iterator()
        while (it.hasNext()) {
            val virtualFloatingView = it.next()
            if (floatingIntent.targetClass.isInstance(virtualFloatingView)) {
                mWindowManager.removeView(virtualFloatingView.realFloatingView) //移除
                virtualFloatingView.onDestroy()
                mFloatingViews.remove(virtualFloatingView)
            }

        }
    }

}
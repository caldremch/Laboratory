package com.caldremch.floatingwindow

import android.os.Bundle

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/1 15:55
 *
 * @description
 *
 *
 */
object FloatingViewLauncher {

    /**
     * 使用队列管理, 多条浮窗的展示, 每条消息, 间隔3s
     */
    fun enqueue(
        targetClass: Class<out AbsFloatingView>,
        bundle: Bundle? = null
    ){
        val intent =  FloatingIntent(targetClass, bundle);
        intent.`when` = 3000
        InternalFloatingViewInitializer.EXECUTOR.enqueue(intent)
    }

    fun launchFloating(
        targetClass: Class<out AbsFloatingView>,
        bundle: Bundle? = null
    ) {
        val intent = FloatingIntent(targetClass)
        intent.bundle = bundle
        AppViewManager.INSTANCE.attach(intent)
    }

    fun destroyFloating(targetClass: Class<out AbsFloatingView>) {
        val intent = FloatingIntent(targetClass)
        AppViewManager.INSTANCE.destroy(intent)
    }

}
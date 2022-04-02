package com.caldremch.floatingwindow

import android.content.Context
import android.view.WindowManager

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/1 14:43
 *
 * @description
 *
 *
 */
internal class AppViewManager : IFloatingViewManager {
    companion object {
        @JvmStatic
        val INSTANCE: AppViewManager by lazy {
            AppViewManager()
        }
        private const val TAG = "AppViewManagerProxy"
    }


    private var _floatingViewManager: IFloatingViewManager? = null

    val windowManager: WindowManager
        get() = Utils.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    override fun attach(floatingIntent: FloatingIntent) {
        ensureViewManager().attach(floatingIntent)
    }

    override fun attachToRecover() {
        ensureViewManager().attachToRecover()
    }

    override fun detach() {
        ensureViewManager().detach()
    }


    override fun destroy(floatingIntent: FloatingIntent) {
        ensureViewManager().destroy(floatingIntent)
    }


    @Synchronized
    private fun ensureViewManager(): IFloatingViewManager {
        return _floatingViewManager
            ?: kotlin.run {
                AppFloatingViewManagerImpl()
            }.also {
                _floatingViewManager = it
            }
    }

}
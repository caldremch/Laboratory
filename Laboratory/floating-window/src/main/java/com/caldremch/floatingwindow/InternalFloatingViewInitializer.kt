package com.caldremch.floatingwindow

import android.app.Application
import android.widget.Toast
import com.caldremch.floatingwindow.schedule.FVSchedule
import com.caldremch.floatingwindow.schedule.FloatingViewSchedule
import com.caldremch.floatingwindow.schedule.FloatingViewThreadFactory
import java.util.concurrent.Executors

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/2 11:24
 *
 * @description
 *
 *
 */
internal object InternalFloatingViewInitializer {

    val EXECUTOR = FVSchedule()

    fun init(app: Application) {
        Utils.context = app
        UtilsActivityLifecycleImpl.INSTANCE.init(app)
        registerAppStatusChangedListener()
        UtilsActivityLifecycleImpl.INSTANCE.addOnAppStatusChangedListener(FloatingViewLauncher.handler)
    }

    /**
     * 注册App前后台切换监听
     */
    private fun registerAppStatusChangedListener() {
        UtilsActivityLifecycleImpl.INSTANCE.addOnAppStatusChangedListener(object :
            OnAppStatusChangedListener {
            override fun onForeground() {
                AppViewManager.INSTANCE.attachToRecover()
            }

            override fun onBackground() {
                AppViewManager.INSTANCE.detach()
            }

        })
    }

}
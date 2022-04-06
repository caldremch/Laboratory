package com.caldremch.floatingwindow.schedule

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.caldremch.floatingwindow.AppViewManager
import com.caldremch.floatingwindow.FloatingIntent
import com.caldremch.floatingwindow.OnAppStatusChangedListener
import java.util.*

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/6 10:42
 *
 * @description
 *
 *
 */
class FloatingViewHandler(private val flowQueue: Deque<FloatingIntent>) :
    OnAppStatusChangedListener,
    Handler(Looper.getMainLooper()) {
    var isRunning = false
    private var onForeground = true
    override fun handleMessage(msg: Message) {
        if (flowQueue.isEmpty()) {
            isRunning = false
            removeCallbacksAndMessages(null)
            return
        }
        val msg = flowQueue.poll()
        if (onForeground) {
            AppViewManager.INSTANCE.attach(msg)
        }
        sendEmptyMessageDelayed(1, 3000)
    }

    override fun onForeground() {
        onForeground = true
    }

    override fun onBackground() {
        onForeground = false

    }

}
package com.caldremch.floatingwindow.schedule

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.caldremch.floatingwindow.*
import com.caldremch.floatingwindow.AppViewManager
import com.caldremch.floatingwindow.InternalFloatingViewInitializer
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
internal class FloatingViewHandler(private val floatMsgQueue: Deque<FloatingIntent>) :
    OnAppStatusChangedListener,
    Handler(Looper.getMainLooper()) {
    var isRunning = false
    private var onForeground = true
    override fun handleMessage(msg: Message) {
        if (floatMsgQueue.isEmpty()) {
            isRunning = false
            removeCallbacksAndMessages(null)
            return
        }
        val floatMsg = floatMsgQueue.poll()
        if (onForeground  && floatMsg !=null) {
            val floatViewType =  floatMsg.floatViewType
            InternalFloatingViewInitializer.onShow?.onSound(floatViewType)?.let { soundRes ->
                SoundManager.play(floatViewType, soundRes)
            }
            AppViewManager.INSTANCE.attach(floatMsg)
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
package com.caldremch.floatingwindow

import android.os.Bundle
import android.os.CountDownTimer
import com.caldremch.floatingwindow.schedule.FVSchedule
import com.caldremch.floatingwindow.schedule.FloatingViewHandler
import java.util.*

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

    private val flowQueue: Deque<FloatingIntent> = LinkedList()

    private class FloatingCountDownTimer(
        millisInFuture: Long,
        countDownInterval: Long,
        private val _flowQueue: Deque<FloatingIntent>
    ) :
        CountDownTimer(millisInFuture, countDownInterval) {

        var isRunning = false;

        override fun onTick(p0: Long) {
            val msg = _flowQueue.poll()
            if (msg == null) {
                cancel()
                isRunning = false;
            } else {
                AppViewManager.INSTANCE.attach(msg)
            }

        }

        override fun onFinish() {
            isRunning = false;
        }

    }

    private class FloatingViewTask : TimerTask() {
        override fun run() {

        }

    }

    private class FloatingTimer : Timer() {

    }

    //定时器
    private var countDownTimer: FloatingCountDownTimer? = null
    private var timer: FloatingTimer? = null


    /**
     * 使用队列管理, 多条浮窗的展示, 每条消息, 间隔3s
     */

     val handler by lazy { FloatingViewHandler(flowQueue) }

    private var nextExecTime = 0L;

    @Synchronized
    fun enqueue(
        targetClass: Class<out AbsFloatingView>,
        bundle: Bundle? = null
    ) {


        flowQueue.offer(FloatingIntent(targetClass, bundle))
        if(!handler.isRunning){
            handler.isRunning = true
            handler.sendEmptyMessage(1)
        }


//        if (timer == null) {
//            timer = FloatingTimer()
//        }
//
//        nextExecTime = System.currentTimeMillis()
//        timer!!.scheduleAtFixedRate(object : TimerTask() {
//            override fun run() {
//                FVSchedule.ANDROID_MAIN_EXECUTOR.post {
//                    val intent = FloatingIntent(targetClass, bundle);
//                    AppViewManager.INSTANCE.attach(intent)
//                }
//            }
//        },0, 3000 )
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
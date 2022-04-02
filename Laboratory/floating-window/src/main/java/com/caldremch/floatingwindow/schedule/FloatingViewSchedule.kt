package com.caldremch.floatingwindow.schedule

import android.os.SystemClock
import android.util.Log
import com.caldremch.floatingwindow.FloatingIntent
import com.caldremch.floatingwindow.FloatingViewLauncher
import java.util.*
import java.util.concurrent.Executors

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/2 16:36
 *
 * @description 浮窗消息调度器
 *
 *
 */
class FloatingViewSchedule {
    private val TAG = "FloatingViewTAG"
    private val OBJECT = Object()

    private fun block(blockTime: Long? = null) {
        if (blockTime != null) {
//            OBJECT.wait(blockTime)
            Thread.sleep(blockTime)
        } else {
//            OBJECT.wait()
        }
    }

    init {
        //启动一个线程, 不销毁的线程
        val factory = FloatingViewThreadFactory()
        val t = factory.newThread {
            Log.d(TAG, ": 浮窗消息线程启动: ${Thread.currentThread().name}")
            while (true) {
                //如果消息队列为空, 则阻塞
                if (messageQueue.isEmpty()) {
                    continue
                }
                val message = messageQueue.peek()
                val now = SystemClock.uptimeMillis()
                if (now < message.`when`) {
                    block(message.`when`)
                }
                //阻塞超时后, 继续执行
                FloatingViewLauncher.launchFloating(message.targetClass, message.bundle)
                //出队
                messageQueue.poll()
            }
        }
        t.start()
    }

    //启动消息线程
    private val e = Executors.newSingleThreadExecutor(FloatingViewThreadFactory())
    private val e1 = Executors.newScheduledThreadPool(1, FloatingViewThreadFactory())

    //消息队列
    private val messageQueue = LinkedList<FloatingIntent>()

    fun submit(message: FloatingIntent) {
        messageQueue.offer(message)
    }

}
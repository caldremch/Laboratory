package com.caldremch.floatingwindow

import android.os.Bundle
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
object FloatingViewManager {

    private val flowQueue: Deque<FloatingIntent> = LinkedList()

    /**
     * 使用队列管理, 多条浮窗的展示, 每条消息, 间隔3s
     */

    internal val handler by lazy { FloatingViewHandler(flowQueue) }



    private var disableIns = hashSetOf<String>()

    fun disableFloatingViewIn(className: String) {
        disableIns.add(className)
    }

    @Synchronized
    fun enqueue(
        targetClass: Class<out AbsFloatingView>,
        bundle: Bundle? = null, type:Int
    ) {
        flowQueue.offer(FloatingIntent(targetClass, bundle, type))
        if (!handler.isRunning) {
            handler.isRunning = true
            handler.sendEmptyMessage(1)
        }
    }

    @Synchronized
    fun enqueue(
        targetClass: Class<out AbsFloatingView>,
        bundle: Bundle? = null
    ) {
        flowQueue.offer(FloatingIntent(targetClass, bundle))
        if (!handler.isRunning) {
            handler.isRunning = true
            handler.sendEmptyMessage(1)
        }
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
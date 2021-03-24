package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.view.Choreographer
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.tencent.matrix.trace.constants.Constants
import com.tencent.matrix.trace.core.UIThreadMonitor
import com.tencent.matrix.util.ReflectUtils

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 3/4/21 09:43
 *
 * @description
 *
 *
 */
@Entry
class ChoreographerEntry : IEntry {

    override fun getTitle(): String {
        return "ChoreographerEntry"
    }

    override fun onClick(context: Context) {
        var choreographer = Choreographer.getInstance()
        choreographer = Choreographer.getInstance()
        val callbackQueueLock = ReflectUtils.reflectObject(choreographer, "mLock", Any())
        val callbackQueues =
            ReflectUtils.reflectObject<Array<Any>>(choreographer, "mCallbackQueues", null)
        if (null != callbackQueues) {
            val addInputQueue = ReflectUtils.reflectMethod(
                callbackQueues.get(UIThreadMonitor.CALLBACK_INPUT), UIThreadMonitor.ADD_CALLBACK,
                Long::class.javaPrimitiveType,
                Any::class.java,
                Any::class.java
            )
            val addAnimationQueue = ReflectUtils.reflectMethod(
                callbackQueues.get(UIThreadMonitor.CALLBACK_ANIMATION),
                UIThreadMonitor.ADD_CALLBACK,
                Long::class.javaPrimitiveType,
                Any::class.java,
                Any::class.java
            )
            val addTraversalQueue = ReflectUtils.reflectMethod(
                callbackQueues.get(UIThreadMonitor.CALLBACK_TRAVERSAL),
                UIThreadMonitor.ADD_CALLBACK,
                Long::class.javaPrimitiveType,
                Any::class.java,
                Any::class.java
            )
        }
        val vsyncReceiver =
            ReflectUtils.reflectObject<Any>(choreographer, "mDisplayEventReceiver", null)
        val frameIntervalNanos = ReflectUtils.reflectObject(
            choreographer,
            "mFrameIntervalNanos",
            Constants.DEFAULT_FRAME_DURATION
        )

    }
}
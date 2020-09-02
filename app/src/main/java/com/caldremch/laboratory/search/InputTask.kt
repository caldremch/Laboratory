package com.caldremch.laboratory.search

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.caldremch.laboratory.activity.SearchTaskActivity
import java.lang.ref.WeakReference

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-01 11:08
 *
 * @email caldremch@163.com
 *
 * @describe 单任务
 *
 **/
class InputTask(activity: SearchTaskActivity) : IExecTask {

    private val task = DelayTask(activity)

    private class DelayTask(activity: SearchTaskActivity) : Handler(Looper.getMainLooper()) {
        private val TAG = "SearchTaskActivity"
        var hasTask = false
        private val weakRef: WeakReference<SearchTaskActivity> = WeakReference(activity)

        override fun handleMessage(msg: Message) {
            hasTask = false
            Log.d(TAG, "handleMessage 任务执行: ${msg.obj?.toString()}")
            weakRef.get()?.execGetDataTask()
        }
    }

    override fun exec(data: Any?, delay: Long) {
        val message = task.obtainMessage()
        message.what = 1; //准备对发起面板展示, 如果
        message.obj = data
        task.hasTask = true
        task.sendMessageDelayed(message, delay)
    }

    override fun cancel() {
        task.hasTask = false
        task.removeCallbacksAndMessages(null)
    }

    override fun isRunning(): Boolean {
        return task.hasTask
    }

}
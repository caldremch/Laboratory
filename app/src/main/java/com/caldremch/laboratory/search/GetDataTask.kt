package com.caldremch.laboratory.search

import android.os.Handler
import android.os.Looper
import com.caldremch.laboratory.activity.SearchTaskActivity
import java.lang.ref.WeakReference

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-01 11:19
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class GetDataTask(activity: SearchTaskActivity) : IExecTask {

    private val weakRef: WeakReference<SearchTaskActivity> = WeakReference(activity)

    private val handler = Handler(Looper.getMainLooper())
    private var hasTask = false

    override fun exec(data: Any?, delay: Long) {
        hasTask = true
        handler.postDelayed(Runnable {
            weakRef.get()?.onGetDataSuccess()
            hasTask = false
        }, 3000)
    }

    override fun cancel() {
        hasTask = false
        handler.removeCallbacksAndMessages(null)
    }

    override fun isRunning(): Boolean {
        return hasTask
    }
}
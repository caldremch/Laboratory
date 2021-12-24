package com.caldremch.common.util

import android.util.Log

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-02 09:25
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class DefaultLogger : CommonLog.Logger {

    private val TAG = "Laboratory"

    override fun d(message: String) {
        Log.d(TAG, message)
//
//        message.split(NEW_LINE_REGEX).forEach {line ->
//            Log.d("TAG", line)
//        }
    }

    override fun d(throwable: Throwable, message: String) {
        d("$message\n${Log.getStackTraceString(throwable)}")
    }

    companion object {
        val NEW_LINE_REGEX = "\n".toRegex()
    }
}
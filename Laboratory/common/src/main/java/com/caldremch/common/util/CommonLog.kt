package com.caldremch.common.util

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-02 09:16
 *
 * @email caldremch@163.com
 *
 * @describe by leakcanary
 *
 **/
object CommonLog {

    interface Logger {
        fun d(message: String)
        fun d(throwable: Throwable, message: String)
    }

    @Volatile
    var logger: Logger? = null

    inline fun d(message: () -> String) {
        val logger = logger ?: return
        logger.d(message.invoke())
    }

    inline fun d(throwable: Throwable, message: () -> String) {
        val logger = logger ?: return
        logger.d(throwable, message.invoke())
    }

}
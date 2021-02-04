package com.caldremch.android.log

import androidx.annotation.Keep

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/1/24 18:59
 *
 * @description
 *
 *
 */
@Keep
object NativeLogger {
    init {
        System.loadLibrary("native-lib")
    }

    @Keep
    external fun saveToFile(path: String, content: String)
}
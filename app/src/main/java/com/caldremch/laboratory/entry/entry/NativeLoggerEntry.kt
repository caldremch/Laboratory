package com.caldremch.laboratory.entry.entry

import android.content.Context
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/1/24 19:45
 *
 * @description
 *
 *
 */
@Entry
class NativeLoggerEntry : IEntry {
    override fun getTitle(): String {
        return "NativeLogger"
    }

    override fun onClick(context: Context) {
        val path = context.getExternalFilesDir("test_mmap")?.absolutePath + "/a.txt"
//        NativeLogger.saveToFile(path, "i am content hahahh")
//        val a = 1 / 0
//        Process.killProcess(Process.myPid())
    }
}
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


    }
}
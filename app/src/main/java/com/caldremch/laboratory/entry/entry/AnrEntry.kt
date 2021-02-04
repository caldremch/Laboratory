package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.os.SystemClock
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-19 14:42
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@Entry
class AnrEntry : IEntry {

    override fun getTitle(): String {
        return "ANR"
    }

    override fun onClick(context: Context) {
        SystemClock.sleep(10000)
    }

}
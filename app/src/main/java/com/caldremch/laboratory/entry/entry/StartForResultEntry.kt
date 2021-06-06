package com.caldremch.laboratory.entry.entry

import android.content.Context
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.activity.MainActivity
import com.caldremch.laboratory.startActivity

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
class StartForResultEntry : IEntry {

    override fun getTitle(): String {
        return "StartForResultEntry"
    }

    override fun onClick(context: Context) {
        context.startActivity<MainActivity>()
    }

}
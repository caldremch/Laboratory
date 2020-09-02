package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.content.Intent
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.PageStatusViewActivity

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
//@Entry
class PageStatusEntry : IEntry {
    override fun getTitle(): String {
        return "页面状态测试"
    }

    override fun onClick(context: Context) {
        context as Context
        context.startActivity(Intent(context, PageStatusViewActivity::class.java))
    }
}
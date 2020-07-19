package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.content.Intent
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
class PageStatusEntry : IEntry {
    override val title: String = "页面状态测试"
    override fun onClick(context: Context) {
        context.startActivity(Intent(context, PageStatusViewActivity::class.java))
    }
}
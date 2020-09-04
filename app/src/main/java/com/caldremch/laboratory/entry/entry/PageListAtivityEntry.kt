package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.content.Intent
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.activity.PageListActivity

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
class PageListAtivityEntry : IEntry {
    override fun getTitle(): String {
        return "Activity分页测试"
    }

    override fun onClick(any: Context) {
        val context = any as Context
        context.startActivity(Intent(context, PageListActivity::class.java))

    }
}
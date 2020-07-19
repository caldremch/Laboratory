package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.content.Intent
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
class PageListAtivityEntry : IEntry {
    override val title: String = "Activity分页测试"
    override fun onClick(context: Context) {
        context.startActivity(Intent(context, PageListActivity::class.java))
    }
}
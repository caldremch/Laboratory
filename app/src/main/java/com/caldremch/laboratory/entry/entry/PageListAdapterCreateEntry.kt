package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.content.Intent
import com.caldremch.laboratory.activity.PageListAdapterActivity

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
class PageListAdapterCreateEntry : IEntry {
    override val title: String = "分页Adapter创建"
    override fun onClick(context: Context) {
        context.startActivity(Intent(context, PageListAdapterActivity::class.java))
    }
}
package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.content.Intent
import com.caldremch.android.annotation.entry.IEntry
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
//@Entry
class PageListAdapterCreateEntry : IEntry {
    override fun getTitle(): String {
        return "分页Adapter创建"
    }

    override fun onClick(context: Context) {
        (context as Context).startActivity(
            Intent(
                context as Context,
                PageListAdapterActivity::class.java
            )
        )
    }
}
package com.caldremch.laboratory.entry.entry

import android.content.Context
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.fragment.PageListFragment
import com.caldremch.laboratory.util.FragmentUtil

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
class PageListEntry : IEntry {

    override fun getTitle(): String {
        return "Fragment分页测试"
    }

    override fun onClick(context: Context) {
        FragmentUtil.add(context as Context, PageListFragment())
    }
}
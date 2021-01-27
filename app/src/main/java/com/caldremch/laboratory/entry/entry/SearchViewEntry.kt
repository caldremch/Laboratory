package com.caldremch.laboratory.entry.entry

import android.content.Context
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.fragment.SearchViewFragment
import com.caldremch.laboratory.util.FragmentUtil

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-01 10:40
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@Entry
class SearchViewEntry : IEntry {
    override fun getTitle(): String {
        return "搜索View"
    }

    override fun onClick(context: Context) {
        FragmentUtil.add(context, SearchViewFragment())
    }
}
package com.caldremch.laboratory.entry.entry

import android.content.Context
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.fragment.ViewEntryFragment
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
@Entry
class ViewActivityEntry : IEntry {

    override fun getTitle(): String {
        return "ViewActivityEntry"
    }

    override fun onClick(context: Context) {
        FragmentUtil.add(context as Context, ViewEntryFragment())
    }

}
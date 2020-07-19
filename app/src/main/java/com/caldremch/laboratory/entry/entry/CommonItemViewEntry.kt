package com.caldremch.laboratory.entry.entry

import android.content.Context
import com.caldremch.laboratory.fragment.CommonItemViewFragment
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
class CommonItemViewEntry : IEntry {
    override val title: String = "CommonItemView Demo"
    override fun onClick(context: Context) {
        FragmentUtil.add(context, CommonItemViewFragment())
    }
}
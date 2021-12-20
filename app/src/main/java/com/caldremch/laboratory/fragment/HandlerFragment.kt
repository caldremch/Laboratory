package com.caldremch.laboratory.fragment

import android.content.Context
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.laboratory.util.FragmentUtil

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-04 16:39
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class HandlerFragment : LaboratoryFragment() {

    @Entry
    class HandlerFragmentEntry : IEntry {
        override fun getTitle(): String {
            return "HandlerFragment"
        }
        override fun onClick(context: Context) {
            FragmentUtil.add(context, HandlerFragment())
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_handler
    }

}
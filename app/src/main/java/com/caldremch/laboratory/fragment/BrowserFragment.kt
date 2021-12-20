package com.caldremch.laboratory.fragment

import android.content.Context
import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.android.entry.EntryRecyclerView
import com.caldremch.laboratory.BrowserActivity
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.laboratory.databinding.FragmentBrowserBinding
import com.caldremch.laboratory.databinding.FragmentHandlerBinding
import com.caldremch.laboratory.util.FragmentUtil

/**
 *
 * @author Caldremch
 *
 * @date 2021/12/20
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class BrowserFragment : LaboratoryFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_browser
    }

    override fun initView() {
        val list = arrayListOf<IEntry>()
        list.add(object : IEntry {
            override fun getTitle(): String {
                return "ThreadLocal"
            }

            override fun onClick(context: Context) {
                BrowserActivity.open(context, "https://www.cnblogs.com/fengzheng/p/8690253.html")
            }
        })
        val rv = findViewById<EntryRecyclerView>(R.id.rv)
        rv.apply {
            setList(list)
        }

    }

}
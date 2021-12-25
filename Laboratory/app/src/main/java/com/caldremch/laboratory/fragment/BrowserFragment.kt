package com.caldremch.laboratory.fragment

import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.android.entry.EntryRecyclerView
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.laboratory.basic.GenericityL
import com.caldremch.laboratory.basic.InstanceGenerationLaboratory
import com.caldremch.laboratory.ktx.entry
import com.caldremch.laboratory.ktx.html

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
        list.add(html("ThreadLocal", "https://www.cnblogs.com/fengzheng/p/8690253.html"))
        list.add(html("Generics", "file:android_asset/Genericity.html"))
        list.add(entry(GenericityL()){
            it.a()
        })
        list.add(entry<InstanceGenerationLaboratory> {
            it.a()
        })
        val rv = findViewById<EntryRecyclerView>(R.id.rv)
        rv.apply {
            setList(list)
        }

    }

}
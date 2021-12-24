package com.caldremch.laboratory.entry.entry

import android.content.Context
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.basic.StringLaboratory
import com.caldremch.laboratory.fragment.BannerFragment
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
class BasicEntry : IEntry {

    override fun getTitle(): String {
        return "BasicEntry"
    }

    override fun onClick(context: Context) {

        val stringLaboratory = StringLaboratory()
        stringLaboratory.a()
        stringLaboratory.b()

    }

}
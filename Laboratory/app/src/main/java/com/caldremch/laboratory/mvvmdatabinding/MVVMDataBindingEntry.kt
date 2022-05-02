package com.caldremch.laboratory.mvvmdatabinding

import android.content.Context
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.fragment.BannerFragment
import com.caldremch.laboratory.fragment.BrowserFragment
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
@Entry
class MVVMDataBindingEntry : IEntry {

    override fun getTitle(): String {
        return this.javaClass.simpleName
    }

    override fun onClick(context: Context) {
        FragmentUtil.add(context, LoginMVVMDataBindingFragment())
    }

}
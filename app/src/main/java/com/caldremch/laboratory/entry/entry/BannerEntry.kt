package com.caldremch.laboratory.entry.entry

import android.content.Context
import com.caldremch.annotation.entry.Entry
import com.caldremch.annotation.entry.IEntry
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
class BannerEntry : IEntry {

    override fun getTitle(): String {
        return "Banner测试"
    }

    override fun onClick(context: Any) {
        FragmentUtil.add(context as Context, BannerFragment())

    }

}
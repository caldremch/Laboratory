package com.caldremch.laboratory.navigation

import android.content.Context
import android.content.Intent
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
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
class NavigationEntry : IEntry {

    override fun getTitle(): String {
        return "navigation demo"
    }

    override fun onClick(context: Context) {
        context.startActivity(Intent(context, NavigationMainActivity::class.java))
    }

}
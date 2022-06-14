package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.util.Log
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.demo.startup.StartupSdkA
import com.caldremch.laboratory.fragment.BannerFragment
import com.caldremch.laboratory.fragment.BrowserFragment
import com.caldremch.laboratory.util.FragmentUtil
import com.caldremch.utils.ToastUtils

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
class StartupEntry : IEntry {

    override fun getTitle(): String {
        return this.javaClass.simpleName
    }

    override fun onClick(context: Context) {
        assert(StartupSdkA.getContext() != null)
        ToastUtils.show(StartupSdkA.getContext().toString())
        Log.d("StartupSdkA", "onClick: ${StartupSdkA.getContext()}")
    }

}
package com.caldremch.laboratory.entry.entry

import android.content.Context
import com.caldremch.annotation.entry.Entry
import com.caldremch.annotation.entry.IEntry
import com.caldremch.laboratory.fragment.NetWatchDogFragment
import com.caldremch.laboratory.util.FragmentUtil

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-25 17:14
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 *
 *  Caused by: java.lang.IllegalAccessException: java.lang.Class<com.caldremch.laboratory.entry.entry.NetEntry$onClick$1$1>
 *      is not accessible from java.lang.Class<com.caldremch.laboratory.MainActivity>
 *
 *   IllegalAccessException: void com.caldremch.laboratory.entry.entry.NetEntry$onClick$listner$1.<init>() is not accessible
 *
 *   在反射调用时, 如果还需要创建匿名类就会报错, 原因未知
 *
 **/
@Entry
class NetEntry : IEntry {
    override val title: String
        get() = "网络监听"

    override fun onClick(context: Any) {
        FragmentUtil.add(context as Context, NetWatchDogFragment())

    }
}
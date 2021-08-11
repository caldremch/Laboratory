package com.caldremch.laboratory.entry.entry

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.utils.ActivityDelegate

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
class ServiceEntry : IEntry {

    override fun getTitle(): String {
        return "service服务测试"
    }

    override fun onClick(context: Context) {

        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val runningServiceInfo = am.getRunningServices(Int.MAX_VALUE)
        Log.d("ServiceEntry", runningServiceInfo.size.toString())

        runningServiceInfo.forEach {
            Log.d("ServiceEntry", it.service.className)
        }

    }

}
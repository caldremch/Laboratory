package com.caldremch.demo.startup

import android.annotation.SuppressLint
import android.content.Context

/**
 * Created by Leon on 2022/6/14
 */
@SuppressLint("StaticFieldLeak")
object StartupSdkA {

    private lateinit var sContext: Context

    fun init(context: Context) {
        sContext = context
    }

    fun getContext() = sContext
}
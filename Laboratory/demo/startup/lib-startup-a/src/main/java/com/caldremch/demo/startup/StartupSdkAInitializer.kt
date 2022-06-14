package com.caldremch.demo.startup

import android.content.Context
import androidx.startup.Initializer

/**
 * Created by Leon on 2022/6/14
 */
class StartupSdkAInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        StartupSdkA.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
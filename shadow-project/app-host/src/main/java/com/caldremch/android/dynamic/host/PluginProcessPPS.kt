package com.caldremch.android.dynamic.host

import android.content.pm.ApplicationInfo
import android.content.res.Resources
import android.util.Log
import com.caldremch.android.host.lib.LoadPluginCallback
import com.tencent.shadow.dynamic.host.PluginProcessService

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/3/30 09:30
 *
 * @description
 *
 *
 */
class PluginProcessPPS : PluginProcessService() {


    init {
        LoadPluginCallback.setCallback(object : LoadPluginCallback.Callback {
            override fun beforeLoadPlugin(partKey: String?) {
                Log.d("PluginProcessPPS", "beforeLoadPlugin($partKey)")

            }

            override fun afterLoadPlugin(
                partKey: String?,
                applicationInfo: ApplicationInfo?,
                pluginClassLoader: ClassLoader?,
                pluginResources: Resources?
            ) {
                Log.d(
                    "PluginProcessPPS",
                    "afterLoadPlugin(" + partKey + "," + applicationInfo!!.className + "{metaData=" + applicationInfo.metaData + "}" + "," + pluginClassLoader + ")"
                )

            }

        })
    }

}
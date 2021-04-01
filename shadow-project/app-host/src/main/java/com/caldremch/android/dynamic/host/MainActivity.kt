package com.caldremch.android.dynamic.host

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tencent.shadow.dynamic.host.EnterCallback

class MainActivity : AppCompatActivity() {


    private val logger = AndroidLogLoggerFactory.getInstance().getLogger(this.javaClass.simpleName)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    object Constant {
        const val KEY_PLUGIN_ZIP_PATH = "pluginZipPath"
        const val KEY_ACTIVITY_CLASSNAME = "KEY_ACTIVITY_CLASSNAME"
        const val KEY_EXTRAS = "KEY_EXTRAS"
        const val KEY_PLUGIN_PART_KEY = "KEY_PLUGIN_PART_KEY"
        const val PART_KEY_PLUGIN_MAIN_APP = "sample-plugin-app"
        const val PART_KEY_PLUGIN_ANOTHER_APP = "sample-plugin-app2"
        const val FROM_ID_NOOP = 1000
        const val FROM_ID_START_ACTIVITY = 1002
    }

    fun startAPlugin(view: View) {
        LoadPluginHelper.getSinglePool().execute {
            //启动插件的 Activity 开始
            val bundle = Bundle()
            //传入目标插件的文件路径.zip
            bundle.putString(
                Constant.KEY_PLUGIN_ZIP_PATH,
                LoadPluginHelper.pluginZipFile.absolutePath
            )
            //传入 part key 标识是要启动的是哪个插件
            bundle.putString(
                Constant.KEY_PLUGIN_PART_KEY,
                "app-plugin-a"
            )
            //传入Activity 的 全限定名
            bundle.putString(
                Constant.KEY_ACTIVITY_CLASSNAME,
                "com.caldremch.android.dynamic.a.APluginActivity"
            )

            HostApp.getApp().loadPluginManager(LoadPluginHelper.pluginManagerFile)
            HostApp.getApp().getPluginManager().enter(this, 1002, bundle, object : EnterCallback {

                override fun onShowLoadingView(p0: View?) {
                    logger.debug("onShowLoadingView")
                }

                override fun onCloseLoadingView() {
                    logger.debug("onCloseLoadingView")
                }

                override fun onEnterComplete() {
                    logger.debug("onEnterComplete")
                }

            })
        }
    }

}
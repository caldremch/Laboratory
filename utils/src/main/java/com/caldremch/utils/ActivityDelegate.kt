package com.caldremch.utils

import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity

/**
 *
 * @author Caldremch
 *
 * @date 2020-11-27 14:26
 *
 * @email caldremch@163.com
 *
 * @describe 作为承载, 获取结果, 不依赖当亲
 *
 **/


class ActivityDelegate {


    companion object {

        private val instance: ActivityDelegate by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityDelegate()
        }
        private lateinit var application: Application
        private var targetIntent: Intent? = null
        private var callback: InnerCallback? = null

        private interface InnerCallback {
            fun callback(isOk: Boolean)
        }

        fun with(): ActivityDelegate {
            if (this::application.isInitialized.not()) throw RuntimeException("init first")
            return instance
        }


        fun init(application: Application) {
            this.application = application
        }
    }


    fun start(targetIntent: Intent, callback: ((isOk: Boolean) -> Unit)) {
        ActivityDelegate.targetIntent = targetIntent
        val host = Intent(application, ActivityDelegateInner::class.java)
        host.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(host)
        ActivityDelegate.callback = object : InnerCallback {
            override fun callback(isOk: Boolean) {
                callback.invoke(isOk)
            }
        }
    }


    class ActivityDelegateInner : FragmentActivity() {

        private val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val isOk = it.resultCode == RESULT_OK
                callback?.callback(isOk)
                targetIntent = null
                callback = null
                finish()
            }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.statusBarColor = Color.TRANSPARENT
            }
            val hideView = View(this).apply { visibility = View.INVISIBLE }
            setContentView(hideView)
            val params = window.attributes
            params.x = 0
            params.y = 0
            params.height = 1
            params.width = 1
            window.attributes = params
            targetIntent?.let {
                launcher.launch(it)
            }
        }


    }

}

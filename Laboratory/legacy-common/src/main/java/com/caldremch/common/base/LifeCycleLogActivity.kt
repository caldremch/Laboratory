package com.caldremch.common.base

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 *
 * @author Caldremch
 *
 * @date 2020-06-28 22:49
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
open class LifeCycleLogActivity : AppCompatActivity() {

    private val TAG = "LifeCycleLogActivity"

    protected fun isPrintLifeCycle(): Boolean {
        return true
    }

    private fun d(log: String) {
        if (isPrintLifeCycle()) {
            Log.d(TAG, this::class.java.simpleName.toString() + "-->" + log)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d("onCreate: ")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        d("onConfigurationChanged: ")
    }

    override fun onStart() {
        super.onStart()
        d("onStart: ")
    }

    override fun onStop() {
        super.onStop()
        d("onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        d("onDestroy: ")
    }

    override fun onPause() {
        super.onPause()
        d("onPause: ")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        d("onNewIntent: ")
    }

    override fun onResume() {
        super.onResume()
        d("onResume: ")
    }

    override fun onRestart() {
        super.onRestart()
        d("onRestart: ")
    }


}
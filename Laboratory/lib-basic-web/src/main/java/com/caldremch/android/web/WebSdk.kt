package com.caldremch.android.web

import android.content.Context
import android.content.Intent

/**
 * Created by Leon on 2022/6/17
 */
object WebSdk {

    fun openUrl(context: Context, url:String){
        val intent = Intent(context, WebActivity::class.java)
        intent.putExtra("url", url)
        context.startActivity(intent)
    }

}
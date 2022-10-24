package com.caldremch.android.web

import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import android.webkit.WebView

/**
 * Created by Leon on 2022/6/17
 */

internal class JsBridge(private val webView: WebView) {

    @JavascriptInterface
    fun documentReady(url:String){
        InnerWebTool.log(url)
    }
    @JavascriptInterface
    fun loadNewUrl(url:String){
        Handler(Looper.getMainLooper()).post {
            InnerWebTool.log("加载的url:$url")
            val newUrl =url+"?time=${System.currentTimeMillis()}"
            webView.loadUrl(newUrl)
            InnerWebTool.log("reload前的url:${webView.url}")
            webView.loadUrl( "javascript:window.location.reload( true )" )
//            webView.loadUrl( "javascript:window.location.replace(${newUrl})" )
            InnerWebTool.log("reload后的url:${webView.url}")
        }

    }

}
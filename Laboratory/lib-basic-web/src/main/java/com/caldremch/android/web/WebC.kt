package com.caldremch.android.web

import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * Created by Leon on 2022/6/17
 */
internal  class WebC : WebViewClient() {

    //重定向判断

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        InnerWebTool.log("onPageStarted(开始加载): $url")
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        InnerWebTool.log("shouldOverrideUrlLoading(重新加载): 目标url:${request?.url}, 当前url=${view?.url}")
        if(true){
            view?.loadUrl(request?.url?.toString()?:"")
            return true
        }
        return false
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        InnerWebTool.log("onPageFinished(结束加载):$url")
    }


}
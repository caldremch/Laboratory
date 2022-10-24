package com.caldremch.android.web

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button

/**
 * Created by Leon on 2022/6/17
 */
internal class WebCC(private val btn: Button) : WebChromeClient() {

    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        InnerWebTool.log("WebView标题设置=$title")
        btn.text = title

    }
}
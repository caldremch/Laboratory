package com.caldremch.android.web

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView.disableWebView
import android.webkit.WebView.setWebContentsDebuggingEnabled
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.android.web.databinding.ActivityWebBinding

/**
 * Created by Leon on 2022/6/17
 */
internal class WebActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityWebBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val url = intent!!.getStringExtra("url")!!

        binding.webView.apply {

            setWebContentsDebuggingEnabled(true);
            val webSettings = settings;
            webSettings.loadWithOverviewMode = true;
            webSettings.domStorageEnabled = true;
            webSettings.useWideViewPort = true;
            // 启用地理定位
            webSettings.setGeolocationEnabled(true);
            webSettings.databaseEnabled = true;
            webSettings.javaScriptEnabled = true;
            webSettings.textZoom = 100;
            webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN;
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            webChromeClient = WebCC(binding.titleView)
            webViewClient = WebC()
            addJavascriptInterface(JsBridge(this), "android")
            loadUrl(url)
        }

        binding.back.setOnClickListener {
            InnerWebTool.log("点击后退:${binding.webView.canGoBack()}")
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            }
        }

    }


}
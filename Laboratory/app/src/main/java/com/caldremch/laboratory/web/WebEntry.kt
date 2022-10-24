package com.caldremch.laboratory.web

import android.content.Context
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.android.web.WebSdk

/**
 * Created by Leon on 2022/6/17
 */
@Entry
class WebEntry : IEntry {
    override fun getTitle(): String {
        return "加载url"
    }

    override fun onClick(context: Context) {
        WebSdk.openUrl(context, "file:///android_asset/demo/demo.html")
    }
}
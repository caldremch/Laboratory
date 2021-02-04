package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.utils.ActivityDelegate

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-19 14:42
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@Entry
class FlowEntry : IEntry {

    override fun getTitle(): String {
        return "FlowWindowEntry"
    }

    override fun onClick(context: Context) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + context.packageName)
        )
        ActivityDelegate.with().start(intent) {

        }
    }

}
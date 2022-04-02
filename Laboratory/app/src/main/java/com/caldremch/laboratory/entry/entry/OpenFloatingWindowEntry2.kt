package com.caldremch.laboratory.entry.entry

import android.content.Context
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.floatingwindow.FloatingViewLauncher
import com.caldremch.floatingwindow.FloatingViewUtil
import com.caldremch.laboratory.floatingview.SimpleFloatView

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
class OpenFloatingWindowEntry2 : IEntry {
    override fun getTitle(): String {
        return "开启悬浮窗(其实是关闭)"
    }

    override fun onClick(context: Context) {
        if(FloatingViewUtil.canDrawOverlays(context).not()){
            FloatingViewUtil.requestDrawOverlays(context)
            return
        }
        FloatingViewLauncher.destroyFloating(SimpleFloatView::class.java)
    }

}
package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.os.Bundle
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.floatingwindow.FloatingViewManager
import com.caldremch.floatingwindow.FloatingViewUtil
import com.caldremch.laboratory.floatingview.SimpleFloatView
import com.caldremch.laboratory.util.FloatingViewType
import java.util.*

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
class OpenFloatingWindowEntry : IEntry {
    override fun getTitle(): String {
        return "开启悬浮窗-type1"
    }


    override fun onClick(context: Context) {
        if (FloatingViewUtil.canDrawOverlays(context).not()) {
            FloatingViewUtil.requestDrawOverlays(context)
            return
        }
        //展示悬浮窗
        val bundle = Bundle()
        bundle.putString("content", "我是悬浮窗啊type1:${Random().nextInt(10000)}")
        FloatingViewManager.enqueue(SimpleFloatView::class.java, bundle, FloatingViewType.TYPE1)
    }


}
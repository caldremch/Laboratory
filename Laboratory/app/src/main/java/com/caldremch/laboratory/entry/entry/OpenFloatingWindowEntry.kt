package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.os.Bundle
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.floatingwindow.FloatingViewLauncher
import com.caldremch.floatingwindow.FloatingViewUtil
import com.caldremch.laboratory.floatingview.SimpleFloatView
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
        return "开启悬浮窗"
    }

    override fun onClick(context: Context) {
        if (FloatingViewUtil.canDrawOverlays(context).not()) {
            FloatingViewUtil.requestDrawOverlays(context)
            return
        }

        //开启10个线程
        for (x in 0..10) {
            Thread {
                //展示悬浮窗
                val bundle = Bundle()
                bundle.putString("content", "我是悬浮窗啊${x}:${Random().nextInt(10000)}")
                FloatingViewLauncher.enqueue(SimpleFloatView::class.java, bundle)
            }.start()
        }
    }


}
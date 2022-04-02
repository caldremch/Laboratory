package com.caldremch.floatingwindow

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.caldremch.floatingwindow.FloatingViewLayoutParams

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2022/4/1 14:53
 * @description
 */
interface FloatingViewLifeCycle {
    fun onCreate(context: Context) {}
    fun onCreateView(context: Context, rootView: FrameLayout?): View
    fun onViewCreated(rootView: FrameLayout?) {}
    fun onUpdate(bundle: Bundle?)
    fun onResume() {}
    fun onPause() {}
    fun initFloatingViewLayoutParams(params: FloatingViewLayoutParams) {}
    fun onEnterBackground()
    fun onEnterForeground()
    fun canDrag(): Boolean = true
    fun onDestroy() {}
}
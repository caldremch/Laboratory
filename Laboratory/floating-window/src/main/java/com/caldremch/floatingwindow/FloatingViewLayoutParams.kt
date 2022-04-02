package com.caldremch.floatingwindow

import android.view.ViewGroup
import android.view.WindowManager

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2022/4/1 14:54
 * @description
 */
class FloatingViewLayoutParams {
    /**
     * 只针对系统悬浮窗起作用 值基本上为以上2个
     */
    var flags = 0

    /**
     * 只针对系统悬浮窗起作用 值基本上为Gravity
     */
    var gravity = 0
    var x = 0
    var y = 0
    var width = 0
    var height = 0
    override fun toString(): String {
        return "FloatingViewLayoutParams{" +
                "flags=" + flags +
                ", gravity=" + gravity +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}'
    }

    companion object {
        /**
         * 悬浮窗不能获取焦点
         */
        var FLAG_NOT_FOCUSABLE = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        var FLAG_NOT_TOUCHABLE = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE

        /**
         * wiki:https://blog.csdn.net/hnlgzb/article/details/108520716
         * 是否允许超出屏幕
         */
        var FLAG_LAYOUT_NO_LIMITS = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS

        /**
         * 悬浮窗不能获取焦点并且不相应触摸
         */
        var FLAG_NOT_FOCUSABLE_AND_NOT_TOUCHABLE =
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        var MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
        var WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT
    }
}
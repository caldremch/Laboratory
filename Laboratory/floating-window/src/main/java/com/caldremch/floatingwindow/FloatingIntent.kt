package com.caldremch.floatingwindow

import android.app.Activity
import android.os.Bundle

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/1 15:13
 *
 * @description  浮窗消息
 *
 *
 */
class FloatingIntent(
    var targetClass: Class<out AbsFloatingView>,
    var bundle: Bundle? = null,
    var floatViewType:Int = 0,
){
    var `when` :Long = 0 //延迟多久执行
    override fun toString(): String {
        return "FloatingIntent(targetClass=$targetClass, bundle=$bundle,  `when`=$`when`)"
    }

}
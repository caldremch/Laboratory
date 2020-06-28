package com.caldremch.dialog.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 *
 * @author Caldremch
 *
 * @date 2020-06-28 11:52
 *
 * @email caldremch@163.com
 *
 * @describe  自动监听键盘, 点击键盘意外的地方收起键盘
 *
 **/
object KBObserver {

    fun init(target: Context) {

        if (target is Activity) {
            val root: View = target.window.decorView
            if (root is ViewGroup) {
                val decorView = root
                if (decorView.childCount > 0) {

                }
            }
        }
    }

}
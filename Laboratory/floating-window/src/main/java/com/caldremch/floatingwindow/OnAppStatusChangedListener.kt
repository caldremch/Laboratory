package com.caldremch.floatingwindow

import android.app.Activity

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/2 11:09
 *
 * @description
 *
 *
 */
interface OnAppStatusChangedListener {
    fun onForeground()
    fun onBackground()
}
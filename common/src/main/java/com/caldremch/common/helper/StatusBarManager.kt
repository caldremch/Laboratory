package com.caldremch.common.helper

import android.app.Activity
import com.gyf.immersionbar.ImmersionBar

/**
 *
 * @author Caldremch
 *
 * @date 2020-06-28 22:53
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class StatusBarManager(val context: Activity) {

    //沉浸式
    protected var mBar: ImmersionBar = ImmersionBar.with(context)

    init {
        mBar.init()
    }

}
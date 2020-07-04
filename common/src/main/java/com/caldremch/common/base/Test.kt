package com.caldremch.common.base

import android.app.Activity
import androidx.fragment.app.Fragment

/**
 * @author Caldremch
 * @date 2020-07-03 15:42
 * @email caldremch@163.com
 * @describe
 */
internal class Test {

    private var fragment: Fragment? = null
    private var activity: Activity? = null

    constructor(fragment: Fragment?) {
        this.fragment = fragment
    }

    constructor(activity: Activity?) {
        this.activity = activity
    }
}
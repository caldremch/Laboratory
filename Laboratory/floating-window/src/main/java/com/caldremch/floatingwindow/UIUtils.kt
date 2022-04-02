package com.caldremch.floatingwindow

import android.content.res.Resources

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/1 16:35
 *
 * @description
 *
 *
 */
internal object UIUtils {

    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}
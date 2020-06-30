package com.caldremch.common.helper

import android.content.Context

/**
 *
 * @author Caldremch
 *
 * @date 2020-06-29 00:41
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object UiHelper {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    @JvmStatic
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

}
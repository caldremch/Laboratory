package com.caldremch.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-12 17:06
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object UiUtils {

    fun getDrawable(@DrawableRes drawableRes: Int): Drawable {
        return ContextCompat.getDrawable(Utils.getContext(), drawableRes)!!
    }

    fun getString(@StringRes stringRes: Int): String {
        return Utils.getContext().getString(stringRes)
    }

    fun getColor(@ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(Utils.getContext(), colorRes)
    }

    fun dp2px(dpValue: Float): Int {
        val scale = Utils.getContext().resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun dp2px(dpValue: Int): Int {
        val scale = Utils.getContext().resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}
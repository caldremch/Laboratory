package com.caldremch.pickerview

import android.content.Context

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-28 10:52
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class Utils() {

    companion object {
        lateinit var myContext: Context
        fun init(context: Context) {
            myContext = context
        }

        fun dp2px(dp: Int): Float {
            val displayMetrics = myContext.resources
                .displayMetrics
            return (dp * displayMetrics.density + 0.5).toFloat()
        }

        fun dp2px(context:Context, dp: Int): Float {
            val displayMetrics = context.resources
                .displayMetrics
            return (dp * displayMetrics.density + 0.5).toFloat()
        }
    }


}
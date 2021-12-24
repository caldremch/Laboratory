package com.caldremch.widget.page.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/10
 *
 * @describe:
 *
 **/

class DefaultErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var tv: TextView = TextView(context)

    init {
        tv.text = "出错了"
        addView(tv)
        gravity = Gravity.CENTER

    }

}
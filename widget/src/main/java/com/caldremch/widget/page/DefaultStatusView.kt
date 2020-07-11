package com.caldremch.widget.page

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/10
 *
 * @describe:
 *
 **/

class DefaultStatusView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), IPageStatus {

    init {
        val tv = TextView(context)
        tv.text = "空页面"
        addView(tv)
        gravity = Gravity.CENTER

    }

    override fun statusView(): View {
        return this
    }

}
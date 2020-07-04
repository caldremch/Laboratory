package com.caldremch.common.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.caldremch.common.R
import kotlinx.android.synthetic.main.view_title_bar.view.*

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-03 23:53
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class TitleBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    interface Listener {

        fun leftClick() {

        }

        fun rightClick() {

        }

        fun titleClick() {

        }
    }

    var listener: Listener? = null

    fun getActionBarHeight(): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)
        val actionBarHeight =
            TypedValue.complexToDimensionPixelSize(typedValue.data, this.resources.displayMetrics);
        return actionBarHeight
    }

    init {
        View.inflate(context, R.layout.view_title_bar, this)
        setBackgroundColor(Color.WHITE)
        id = R.id.android_common_title_view_id
        tv_left.setOnClickListener { listener?.leftClick() }
        tv_right.setOnClickListener { listener?.rightClick() }
        tv_title.setOnClickListener { listener?.titleClick() }
    }

    fun setTitle(title: String?) {
        tv_title?.text = title
    }
}
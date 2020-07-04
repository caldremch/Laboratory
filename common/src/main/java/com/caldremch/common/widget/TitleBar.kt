package com.caldremch.common.widget

import android.content.Context
import android.util.AttributeSet
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

    init {
        View.inflate(context, R.layout.view_title_bar, this)
        id = R.id.android_common_title_view_id

        tv_left.setOnClickListener { listener?.leftClick() }
        tv_right.setOnClickListener { listener?.rightClick() }
        tv_title.setOnClickListener { listener?.titleClick() }
    }
}
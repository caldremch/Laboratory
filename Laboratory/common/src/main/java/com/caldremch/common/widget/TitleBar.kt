package com.caldremch.common.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.common.R
import com.caldremch.common.databinding.CmViewTitleBarBinding

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

    private val binding by viewBinding(CmViewTitleBarBinding::bind)

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
        View.inflate(context, R.layout.cm_view_title_bar, this)
        setBackgroundColor(Color.WHITE)
        id = R.id.cm_android_common_title_view_id
        binding.tvLeft.setOnClickListener { listener?.leftClick() }
        binding.tvRight.setOnClickListener { listener?.rightClick() }
        binding.tvTitle.setOnClickListener { listener?.titleClick() }
    }

    fun setTitle(title: String?) {
        binding.tvTitle.text = title
    }
}
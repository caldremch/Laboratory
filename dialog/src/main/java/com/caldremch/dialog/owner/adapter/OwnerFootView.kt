package com.caldremch.dialog.owner.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.caldremch.dialog.R

/**
 *
 *Created by Caldremch on 2020/6/25.
 *
 **/
class OwnerFootView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.owner_foot_view, this)
        val pading = resources.getDimension(R.dimen.dp_10).toInt()
        setPadding(pading, pading, pading, pading)
    }

}
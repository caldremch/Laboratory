package com.caldremch.dialog.owner

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.dialog.R

/**
 *
 *Created by Caldremch on 2020/6/25.
 *
 **/
class OwnerItemDecorator : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.adapter != null && parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1)
            outRect.bottom = view.context.resources.getDimension(R.dimen.dp_10).toInt()
    }
}
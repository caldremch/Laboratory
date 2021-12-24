package com.caldremch.widget.single

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/6/19
 *
 * @describe:
 *
 **/

class SingleItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.adapter?.let {
            if (parent.getChildAdapterPosition(view) != it.itemCount - 1)
                outRect.right = 30
        }
    }
}
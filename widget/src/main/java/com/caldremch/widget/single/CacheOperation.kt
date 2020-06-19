package com.caldremch.widget.single

import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/6/19
 *
 * @describe: 操作缓存
 *
 **/

class CacheOperation<T : SelectItem, D : SingleSelectHolder>(
    private val rv: RecyclerView,
    private val handle: SingleSelectAdapter<T, D>,
    private val selectedPos: Int,
    private val holder: D,
    private val currentPosition: Int
) {

    fun goOn() {
        val lastSelectedHolder = rv.findViewHolderForLayoutPosition(selectedPos) as D
        handle.handleSelect(
            lastSelectedHolder,
            holder,
            currentPosition
        )
    }
}
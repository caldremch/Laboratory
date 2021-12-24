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

class CacheOperation<T : SelectItem, D : RecyclerView.ViewHolder>(
    private val rv: RecyclerView,
    private val handle: SingleSelectAdapter<T, D>,
    private val selectedPos: Int,
    private val holder: D, //当前选中holder
    private val currentPosition: Int //当前选中position
) {

    fun goOn() {

        val lastSelectedHolder: D?
        lastSelectedHolder = if (selectedPos == SingleSelectAdapter.NONE) {
            null
        } else {
            val resultHolder = rv.findViewHolderForLayoutPosition(selectedPos)
            if (resultHolder == null) {
                null
            } else {
                resultHolder as D
            }
        }

        handle.handleSelect(
            lastSelectedHolder,
            holder,
            currentPosition
        )
    }
}
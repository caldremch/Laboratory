package com.caldremch.pickerview.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-28 00:59
 *
 * @email caldremch@163.com
 *
 * @describe 3D recyclerView适配器,
 *
 **/
abstract class WheelAdapter<T : RecyclerView.ViewHolder> {

    var adapter: RecyclerView.Adapter<T>? = null

    abstract fun getItemCount(): Int

    open fun getItemViewType(position: Int): Int {
        return 0
    }

    abstract fun onCreateViewHolder(inflater: LayoutInflater, viewType: Int): T

    abstract fun onBindViewHolder(holder: T, position: Int)

    open fun onViewRecycled(holder: T) {}

    open fun onViewAttachedToWindow(holder: T) {}

    open fun onViewDetachedFromWindow(holder: T) {}

    fun notifyDataSetChanged() {
        adapter?.notifyDataSetChanged()
    }
}
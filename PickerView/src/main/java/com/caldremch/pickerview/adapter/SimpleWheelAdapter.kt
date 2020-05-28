package com.caldremch.pickerview.adapter

import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.caldremch.pickerview.Utils
import com.caldremch.pickerview.WheelUtils

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-28 01:06
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
open class SimpleWheelAdapter<T : ViewHolder>(

    var adapter: WheelAdapter<T>,
    private var orientation: Int,//recyclerview 布局方向
    var itemSize: Int, //item 高度
    var myItemCount: Int, //上或下的 item 数量
    var itemWidth: Float

) : RecyclerView.Adapter<T>() {

    /**
     * 空白状态
     */
    val EMPTY_TYPE = 0x00000222

    /**
     * wheelview 滑动时上或下空白总数量
     */
    private var totalItemCount = 0

    private var inflater: LayoutInflater? = null

    init {
        totalItemCount = myItemCount * 2
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val params = ViewGroup.LayoutParams(itemWidth.toInt(), itemSize)

        if (viewType == EMPTY_TYPE) {
            val view = View(parent.context)
            view.layoutParams = params
            return EmptyViewHolder(view) as T
        }
        val vh = adapter.onCreateViewHolder(inflater!!, viewType)
        vh.itemView.layoutParams = params
        Log.d("tag", "onCreateViewHolder=${params.width}-->$itemWidth")
        return vh
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        if (holder.itemViewType == EMPTY_TYPE) return
        adapter.onBindViewHolder(holder, actualPosition(position))
    }


    override fun getItemCount(): Int {
        return totalItemCount + adapter.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        //空白 item 控制
        if (position < myItemCount || position >= adapter.getItemCount() + myItemCount) {
            return EMPTY_TYPE
        }
        return adapter.getItemViewType(actualPosition(position))
    }


    override fun onViewRecycled(holder: T) {
        if (holder.itemViewType == EMPTY_TYPE) return
        adapter.onViewRecycled(holder)
    }

    override fun onViewAttachedToWindow(holder: T) {
        if (holder.itemViewType == EMPTY_TYPE) return
        adapter.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: T) {
        if (holder.itemViewType == EMPTY_TYPE) return
        adapter.onViewDetachedFromWindow(holder)
    }

    /**
     * 实际位置
     */
    private fun actualPosition(position: Int): Int {
        return position - myItemCount
    }


}

/**
 * 空白ViewHolder
 */
class EmptyViewHolder(itemView: View) : ViewHolder(itemView)
package com.caldremch.widget.single

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.widget.R

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/6/19
 *
 * @describe:
 *
 **/

class UnitTypeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv: TextView = itemView.findViewById(R.id.tv)
}

class SingleAdapter(data: List<StringItem>, rv: RecyclerView, selectedPos: Int = -1) :
    SingleSelectAdapter<StringItem, UnitTypeHolder>(data, rv, selectedPos, true) {

    override fun bindViewHolder(holder: UnitTypeHolder, position: Int, data: StringItem) {
        holder.tv.text = data.string
        holder.tv.isSelected = data.isSelect
    }

    override fun selectInterrupt(): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitTypeHolder {
        return UnitTypeHolder(inflateView(parent, R.layout.single_item))
    }


    override fun onSelectHolder(holder: UnitTypeHolder, position: Int, item: StringItem) {
        holder.tv.isSelected = true
    }

    override fun onUnSelectHolder(holder: UnitTypeHolder, position: Int, item: StringItem) {
        holder.tv.isSelected = false
    }

}
package com.caldremch.widget.single

import android.view.View
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

class UnitTypeHolder(itemView: View) : SingleSelectHolder(itemView) {
    val tv: TextView = getView(R.id.tv)
}

class SingleAdapter(data: List<StringItem>, rv: RecyclerView, selectedPos: Int = 0) :
    SingleSelectAdapter<StringItem, UnitTypeHolder>(data, rv, selectedPos, R.layout.single_item) {

    override fun bindViewHolder(holder: UnitTypeHolder, position: Int, data: StringItem) {
        holder.tv.text = data.string
        holder.tv.isSelected = data.isSelect
    }

    override fun createViewHolder(view: View): UnitTypeHolder {
        return UnitTypeHolder(view)
    }

    override fun onUnSelectHolder(holder: UnitTypeHolder) {
        holder.tv.isSelected = false
    }

    override fun onSelectHolder(holder: UnitTypeHolder) {
        holder.tv.isSelected = true
    }

    override fun selectInterrupt(): Boolean {
        return true
    }

}
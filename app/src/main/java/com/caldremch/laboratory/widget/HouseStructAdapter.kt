package com.caldremch.laboratory.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.laboratory.R
import com.caldremch.widget.single.SingleSelectAdapter
import com.caldremch.widget.single.SingleSelectHolder

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/6/30
 *
 * @describe:
 *
 **/

class HouseStructHolder(itemView: View) : SingleSelectHolder(itemView) {

}

class HouseStructAdapter(
    val mDatas: List<HouseStructValue>,
    rv: RecyclerView,
    selectedPos: Int = -1
) : SingleSelectAdapter<HouseStructValue, HouseStructHolder>(mDatas, rv, selectedPos) {

    override fun onUnSelectHolder(holder: HouseStructHolder) {

    }

    override fun onSelectHolder(holder: HouseStructHolder) {

    }

    override fun bindViewHolder(holder: HouseStructHolder, position: Int, data: HouseStructValue) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseStructHolder {
        val layoutId: Int = if (viewType == 1) {
            R.layout.struct_text
        } else {
            R.layout.struct_edit
        }
        return HouseStructHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (mDatas[position].isEditText) 0 else 1
    }

}
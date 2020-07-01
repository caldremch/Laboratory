package com.caldremch.laboratory.widget

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
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

class HouseStructHolder(itemView: View, adapter: HouseStructAdapter) :
    SingleSelectHolder(itemView) {
    val et = itemView.findViewById<EditText>(R.id.et)
    val tv = itemView.findViewById<TextView>(R.id.tv)
    val ll_edit = itemView.findViewById<View>(R.id.ll_edit)

    //用于控制, 点击选中Edittext时, 并不会选中, 所以点击的时候, 操作是requestFocus
    //requestFocus时, 不需要模拟点击
    var isEditSelect = false

    init {
        et?.setOnFocusChangeListener { v, hasFocus ->
            if (ll_edit != null) {
                if (hasFocus && !isEditSelect) {
                    isEditSelect = false
                    ll_edit.performClick()
                }
            }
        }

        et?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (layoutPosition != -1) {
                    if (TextUtils.isEmpty(s?.toString())) {
                        adapter.mDatas[layoutPosition].value = null
                    } else {
                        adapter.mDatas[layoutPosition].value = s?.toString()?.toInt()
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }
}

class HouseStructAdapter(
    val mDatas: List<HouseStructValue>,
    rv: RecyclerView,
    val type: Int = 0,
    selectedPos: Int = NONE
) : SingleSelectAdapter<HouseStructValue, HouseStructHolder>(mDatas, rv, selectedPos, true) {


    override fun bindViewHolder(holder: HouseStructHolder, position: Int, data: HouseStructValue) {

        when (type) {
            HouseStructValue.ROOM -> {
                if (getItemViewType(position) == 0) {
                    holder.tv.text = "${data.value}室"
                } else {
                    data.value?.let {
                        holder.et.setText("$it")
                    }
                }
            }
            HouseStructValue.HALL -> {
                if (getItemViewType(position) == 0) {
                    holder.tv.text = "${data.value}厅"
                } else {
                    data.value?.let {
                        holder.et.setText("$it")
                    }
                }
            }
            HouseStructValue.TOILET -> {
                if (getItemViewType(position) == 0) {
                    holder.tv.text = "${data.value}卫"
                } else {
                    data.value?.let {
                        holder.et.setText("$it")
                    }
                }
            }
        }

        if (getItemViewType(position) == 0) {
            holder.tv.isSelected = data.isSelect
        } else {
            holder.isEditSelect = data.isSelect
            holder.ll_edit.isSelected = data.isSelect
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseStructHolder {
        val layoutId: Int = if (viewType == 0) {
            R.layout.struct_text
        } else {
            R.layout.struct_edit
        }
        return HouseStructHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            , this
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (mDatas[position].isEditText) 1 else 0
    }

    override fun onSelectHolder(holder: HouseStructHolder, position: Int, item: HouseStructValue) {
        if (getItemViewType(position) == 0) {
            holder.tv.isSelected = true
        } else {
            holder.isEditSelect = true
            holder.ll_edit.isSelected = true
            holder.et.requestFocus()
        }
    }

    override fun onUnSelectHolder(
        holder: HouseStructHolder,
        position: Int,
        item: HouseStructValue
    ) {
        if (getItemViewType(position) == 0) {
            holder.tv.isSelected = false
        } else {
            holder.ll_edit.isSelected = false
            holder.isEditSelect = false
            holder.et.clearFocus()
        }

    }

}
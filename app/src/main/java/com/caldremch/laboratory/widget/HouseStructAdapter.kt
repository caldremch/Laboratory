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
    RecyclerView.ViewHolder(itemView) {

    val et = itemView.findViewById<EditText>(R.id.et)
    val tv = itemView.findViewById<TextView>(R.id.tv)
    val ll_edit = itemView.findViewById<View>(R.id.ll_edit)
    val tv_type_desc = itemView.findViewById<TextView>(R.id.tv_type_desc)

    //用于控制, 点击选中Edittext时, 并不会选中, 所以点击的时候, 操作是requestFocus
    //requestFocus时, 不需要模拟点击
    var isEditSelect = false

    init {

        //焦点获取监听
        et?.setOnFocusChangeListener { v, hasFocus ->
            if (ll_edit != null) {
                if (hasFocus && !isEditSelect) {
                    isEditSelect = false
                    ll_edit.performClick()
                }
            }
        }

        //文本变化
        et?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (layoutPosition != -1) {
                    if (TextUtils.isEmpty(s?.toString())) {
                        adapter.mData[layoutPosition].value = null
                    } else {
                        adapter.mData[layoutPosition].value = s?.toString()?.toInt()
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
    val data: List<HouseStructValue>,
    rv: RecyclerView,
    private val type: Int = 0,
    selectedPos: Int = NONE
) : SingleSelectAdapter<HouseStructValue, HouseStructHolder>(data, rv, selectedPos, true) {


    override fun bindViewHolder(holder: HouseStructHolder, position: Int, data: HouseStructValue) {

        when (type) {
            HouseStructValue.ROOM -> {
                if (getItemViewType(position) == 0) {
                    holder.tv.text = "${data.value}室"
                } else {
                    data.value?.let {
                        holder.et.setText("$it")
                    }
                    holder.tv_type_desc.text = "室"
                }
            }
            HouseStructValue.HALL -> {
                if (getItemViewType(position) == 0) {
                    holder.tv.text = "${data.value}厅"
                } else {
                    data.value?.let {
                        holder.et.setText("$it")
                    }
                    holder.tv_type_desc.text = "厅"
                }
            }
            HouseStructValue.TOILET -> {
                if (getItemViewType(position) == 0) {
                    holder.tv.text = "${data.value}卫"
                } else {
                    data.value?.let {
                        holder.et.setText("$it")
                    }
                    holder.tv_type_desc.text = "卫"
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
        return if (mData[position].isEditText) 1 else 0
    }

    override fun onSelectHolder(holder: HouseStructHolder, position: Int, item: HouseStructValue) {
        if (getItemViewType(position) == 0) {
            holder.tv.isSelected = true
        } else {
            holder.isEditSelect = true
            holder.ll_edit.isSelected = true
            holder.et.requestFocus()
//            val inputManager =
//                rv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            inputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED)
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
            holder.et.setText(null)
            holder.et.clearFocus()
        }

    }

}
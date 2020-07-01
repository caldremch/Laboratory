package com.caldremch.dialog.owner

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.caldremch.dialog.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *
 *Created by Caldremch on 2020/6/25.
 *
 * 处理了EditText在 RecyclerView 中的复用,监听情况, 翻遍以后参考[该列表最多6个, 用不到]
 * BindViewHolder 里添加监听的时候, 会因为TextWatcher导致, 快速滑动会错乱
 *
 **/


class OwnerAdapter :
    BaseQuickAdapter<Contact, OwnerAdapter.OwnerViewHolder>(R.layout.item_owner, mutableListOf()) {

    interface Listener {
        fun remove(index: Int)
    }

    var isMaskPhone: Boolean = false
    var listener: Listener? = null

    override fun convert(holder: OwnerViewHolder, item: Contact) {


        if (holder.layoutPosition == 0 || !item.isEnable) {
            //第一个不可删除
            holder.iv.setImageResource(R.drawable.ic_typein_customer)
        } else {
            holder.iv.setImageResource(R.drawable.ic_edit_remove)
        }

        holder.iv.setOnClickListener {
            if (holder.layoutPosition == 0 || !item.isEnable) {
                return@setOnClickListener
            }
            listener?.remove(holder.layoutPosition)
        }

        holder.nameEt.isEnabled = item.isEnable
        holder.phoneEt.isEnabled = item.isEnable

//        holder.nameEt.setText(item.name)
//        holder.phoneEt.setText(if (isMaskPhone) item.maskPhone else item.phone)
//        holder.nameEt.setText(item.name)
//        holder.phoneEt.setText(item.phone)

//        holder.listener = object : OwnerEditListener{
//            override fun onTextChange(string: String?) {
//                holder.nameEt.setText(string)
//            }
//        }

    }



    inner class OwnerViewHolder(view: View) : BaseViewHolder(view) {

        var listener: OwnerEditListener? = null

        var nameEt = getView<EditText>(R.id.et_name)
        var phoneEt = getView<EditText>(R.id.et_phone)
        val iv = getView<ImageView>(R.id.iv)

        init {
            /***********************onCreateViewHolder中添加监听, 更新对应位置**************************/
            /*nameEt.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    data[layoutPosition].name = s?.toString()
                    listener?.onTextChange(s?.toString())
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
            phoneEt.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    data[layoutPosition].phone = s?.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

             */

        }
    }
}
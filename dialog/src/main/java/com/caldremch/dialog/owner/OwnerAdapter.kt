package com.caldremch.dialog.owner

import com.caldremch.dialog.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *
 *Created by Caldremch on 2020/6/25.
 *
 **/
class OwnerAdapter :
    BaseQuickAdapter<Contact, BaseViewHolder>(R.layout.item_owner, mutableListOf()) {
    override fun convert(holder: BaseViewHolder, item: Contact) {
        holder.setText(R.id.et_name, item.name)
        holder.setText(R.id.et_phone, item.phone)
    }
}
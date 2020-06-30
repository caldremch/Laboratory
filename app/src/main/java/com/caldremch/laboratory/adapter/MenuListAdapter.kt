package com.caldremch.laboratory.adapter

import com.caldremch.laboratory.R
import com.caldremch.laboratory.bean.MenuData
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *
 *Created by Caldremch on 2020/6/25.
 *
 **/
class MenuListAdapter :
    BaseQuickAdapter<MenuData, BaseViewHolder>(R.layout.item_menu, mutableListOf()) {

    override fun convert(holder: BaseViewHolder, item: MenuData) {
        holder.setText(R.id.tv, item.title)

    }
}
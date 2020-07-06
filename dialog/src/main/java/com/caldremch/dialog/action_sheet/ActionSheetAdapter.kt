package com.caldremch.dialog.action_sheet

import android.widget.ImageView
import com.caldremch.dialog.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/6
 *
 * @describe:
 *
 **/

class ActionSheetAdapter(list: MutableList<ActionData>?) :
    BaseQuickAdapter<ActionData, BaseViewHolder>(R.layout.item_action_sheet, list),
    DraggableModule {
    override fun convert(holder: BaseViewHolder, item: ActionData) {
        val imageView = holder.getView<ImageView>(R.id.iv)
        if (item.imageRes != null) {
            imageView.setImageResource(item.imageRes!!)
        }
        holder.setText(R.id.tv, item.title)
    }

}
package com.caldremch.dialog.action_sheet

import com.caldremch.dialog.R

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/6
 *
 * @describe:
 *
 **/

class ActionSheetDialog(parent: Any) : BaseActionSheetDialog(parent) {

    override fun getTitleViewId(): Int {
        return R.layout.action_sheet_title
    }

    init {
        dragListener = ActionSheetDragListener { startPos, endPos, data -> }
    }

}
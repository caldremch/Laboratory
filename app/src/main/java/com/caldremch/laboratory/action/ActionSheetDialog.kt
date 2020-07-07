package com.caldremch.laboratory.action

import com.caldremch.dialog.R
import com.caldremch.dialog.action_sheet.ActionSheetDragListener
import com.caldremch.dialog.action_sheet.BaseActionSheetDialog
import com.caldremch.laboratory.TargetData

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/6
 *
 * @describe:
 *
 **/

class ActionSheetDialog(parent: Any) : BaseActionSheetDialog<TargetData>(parent) {

    override fun getTitleViewId(): Int {
        return R.layout.action_sheet_title
    }

    init {
        dragListener = ActionSheetDragListener { startPos, endPos, data -> }
    }

    override fun getData(): TargetData? {
        return null;
    }

}
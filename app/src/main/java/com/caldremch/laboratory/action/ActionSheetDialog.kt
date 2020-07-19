package com.caldremch.laboratory.action

import android.content.Context
import com.caldremch.dialog.action_sheet.ActionSheetDragListener
import com.caldremch.dialog.action_sheet.BaseActionSheetDialog
import com.caldremch.dialog.action_sheet.IActionHeader
import com.caldremch.dialog.action_sheet.IData

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/6
 *
 * @describe:
 *
 **/

class ActionSheetDialog(parent: Context) : BaseActionSheetDialog(parent) {

    val testBundleData = TestBundleData()

    override fun getTitleView(): IActionHeader? {
        return TestActionHeaderView(mContext)
    }

    init {
        dragListener = ActionSheetDragListener { startPos, endPos, data -> }
    }

    override fun getData(): IData? {
        return testBundleData;
    }


}
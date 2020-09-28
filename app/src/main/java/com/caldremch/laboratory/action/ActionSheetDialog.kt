package com.caldremch.laboratory.action

import android.content.Context
import com.caldremch.dialog.action_sheet.ActionSheetDragListener
import com.caldremch.dialog.action_sheet.BaseActionData
import com.caldremch.dialog.action_sheet.BaseActionSheetDialog
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

    init {
        dragListener =
            ActionSheetDragListener { i: Int, i1: Int, mutableList: MutableList<BaseActionData>, i2: Int ->

            }
    }

    override fun getData(): IData? {
        return testBundleData;
    }


}
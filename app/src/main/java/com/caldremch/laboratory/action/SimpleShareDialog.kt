package com.caldremch.laboratory.action

import com.caldremch.dialog.action_sheet.BaseActionSheetDialog
import com.caldremch.laboratory.TargetData

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/7
 *
 * @describe: 简单分享
 *
 **/

class SimpleShareDialog(parent: Any) : BaseActionSheetDialog<TargetData>(parent) {
    override fun getData(): TargetData? {
        return null
    }

}
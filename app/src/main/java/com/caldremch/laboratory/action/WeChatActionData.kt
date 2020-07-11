package com.caldremch.laboratory.action

import android.content.Context
import android.os.Parcelable
import android.widget.Toast
import com.caldremch.dialog.R
import com.caldremch.dialog.action_sheet.ActionData
import com.caldremch.dialog.action_sheet.BaseActionData
import com.caldremch.dialog.action_sheet.IData
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/7
 *
 * @describe:
 *
 **/

@Parcelize
class WeChatActionData : BaseActionData, Parcelable {

    val actionData = ActionData()

    init {
        actionData.imageRes = R.drawable.ic_edit_remove
        actionData.title = "微信分享"
    }

    override fun getData(): ActionData {
        return actionData
    }

    override fun onClick(context: Context, data: IData?) {
        Toast.makeText(context, "微信分享", Toast.LENGTH_SHORT).show()
    }
}
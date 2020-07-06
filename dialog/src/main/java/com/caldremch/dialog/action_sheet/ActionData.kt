package com.caldremch.dialog.action_sheet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/6
 *
 * @describe: action sheet item数据
 *
 * 1.图片[本地资源/url网络图片]
 * 2.标题
 * 3.消息数目
 * 4.点击事件 [Action]
 * 5.类型支持 type
 *
 **/
@Parcelize
data class ActionData(
    var title: String? = null, //标题
    var imageRes: Int? = null, //本地资源图片
    var imageUrl: String? = null, //网络图片资源
    var msgCount: Int? = null, //消息条数
    var action: @RawValue Action? = null, //item点击操作
    var type: Int? = null //item类型设置
) : Parcelable
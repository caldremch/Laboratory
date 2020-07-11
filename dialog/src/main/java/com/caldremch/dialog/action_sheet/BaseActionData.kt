package com.caldremch.dialog.action_sheet

import android.content.Context
import android.os.Parcelable

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/7
 *
 * @describe: 在同一组ActionSheet列表中, T必须是相同的
 * 每个actionsheet都有一个引用的 数据源
 * ,
 * 根据数据源来进行数据填充和运行时的一些操作, 封装起来, 可以复用
 *
 * 1.是否显示 [依赖数据源]
 * 2.排序问题 [依赖数据源]
 * 3.跳转 [依赖数据源]
 *
 **/

interface BaseActionData : Parcelable {
    fun getData(): ActionData
    fun onClick(context: Context, data: IData?)
}
package com.caldremch.dialog.action_sheet

import android.view.View

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/10
 *
 * @describe:
 *
 **/

interface IActionHeader {
    fun initData(data: IData?)
    fun getHeaderView(): View
}
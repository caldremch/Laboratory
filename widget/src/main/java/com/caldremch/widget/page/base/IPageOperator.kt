package com.caldremch.widget.page.base

import android.view.View

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/10
 *
 * @describe: Activity/Fragment 所能控制分页控制的操作
 * 只能处理数据源
 *
 **/

interface IPageOperator<T> {
    fun handleData(pageData: List<T>?)
    fun handleError()
    fun getPageView(): View
}
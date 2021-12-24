package com.caldremch.widget.page.protocol

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

interface IPageOperator<T> : ICustomerConfig<T> {
    fun handleData(pageData: List<T>?)
    fun handleError()
    fun refresh()
    fun getPageView(): View
}
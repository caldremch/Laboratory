package com.caldremch.widget.page.protocol

import android.view.View

/**
 * @author Caldremch
 * @date  2020/7/9
 * @email caldremch@163.com
 * @describe
 *
 **/
interface IPageLoading {
    fun startLoading()
    fun stopLoading()
    fun loadingView(): View
}
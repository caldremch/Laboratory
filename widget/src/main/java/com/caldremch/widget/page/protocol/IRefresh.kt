package com.caldremch.widget.page.protocol

import android.view.ViewGroup

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/10
 *
 * @describe:
 *
 **/

interface IRefresh {
    fun setOnLoadMoreListenerEx(listenerEx: OnLoadMoreListenerEx)
    fun setOnRefreshListener(listenerEx: OnRefreshListenerEx)
    fun getView(): ViewGroup
    fun onFinishRefreshAndLoadMore()
    fun setEnableLoadMoreEx(enable: Boolean)
}
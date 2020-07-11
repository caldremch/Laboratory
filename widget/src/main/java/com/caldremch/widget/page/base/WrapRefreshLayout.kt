package com.caldremch.widget.page.base

import android.content.Context
import android.view.ViewGroup
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/10
 *
 * @describe: 方便切换下拉刷新
 *
 **/

class WrapRefreshLayout(context: Context?) : SmartRefreshLayout(context), IRefresh {

    private var onLoadMoreListenerEx: OnLoadMoreListenerEx? = null
    private var onRefreshListenerEx: OnRefreshListenerEx? = null

    init {
        initView()
        initEvent()
    }

    private fun initView() {
        setRefreshHeader(ClassicsHeader(context))
        setRefreshFooter(ClassicsFooter(context))
    }

    private fun initEvent() {
        setOnLoadMoreListener { layout -> onLoadMoreListenerEx?.onLoadMore(layout) }
        setOnRefreshListener { layout -> onRefreshListenerEx?.onRefresh(layout) }
    }

    override fun setOnLoadMoreListenerEx(listenerEx: OnLoadMoreListenerEx) {
        this.onLoadMoreListenerEx = listenerEx
    }

    override fun setOnRefreshListener(listenerEx: OnRefreshListenerEx) {
        this.onRefreshListenerEx = listenerEx
    }

    override fun getView(): ViewGroup {
        return this
    }

    override fun onFinishRefreshAndLoadMore() {
        finishRefresh(0)
        finishLoadMore(0)
    }

    override fun setEnableLoadMoreEx(enable: Boolean) {
        setEnableLoadMore(enable)
    }

}
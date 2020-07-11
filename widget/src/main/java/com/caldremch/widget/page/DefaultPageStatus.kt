package com.caldremch.widget.page

import android.content.Context
import android.view.View

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-11 22:52
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class DefaultPageStatus(val context: Context) : IPageStatus {

    private var currentStatus = PageStatus.EMPTY

    val loadingView = DefaultLoadingView(context)
    val emptyView = DefaultEmptyView(context)
    val errorView = DefaultErrorView(context)
    override fun setStatus(status: PageStatus) {
        currentStatus = status
    }

    override fun startLoading() {
        loadingView.startAnim()
    }

    override fun stopLoading() {
        loadingView.stopAnim()
    }

    override fun statusView(): View {
        when (currentStatus) {
            PageStatus.LOADING -> return loadingView
            PageStatus.EMPTY -> return emptyView
            PageStatus.ERROR -> return errorView
        }
    }
}
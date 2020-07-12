package com.caldremch.widget.page.view

import android.content.Context
import android.view.View
import com.caldremch.widget.page.PageStatus
import com.caldremch.widget.page.protocol.IPageStatus
import com.caldremch.widget.page.protocol.IRetryListener

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
class DefaultPageStatus(val context: Context) :
    IPageStatus {

    private var retryListenerListener: IRetryListener? = null
    val loadingView = DefaultLoadingView(context)
    val emptyView = DefaultEmptyView(context)
    val errorView = DefaultErrorView(context)

    init {
        errorView.tv.setOnClickListener {
            retryListenerListener?.onRetry()
        }
    }

    override fun setRetryListener(retryListenerListener: IRetryListener?) {
        this.retryListenerListener = retryListenerListener
    }

    override fun startLoading() {
        loadingView.startAnim()
    }

    override fun stopLoading() {
        loadingView.stopAnim()
    }

    override fun statusView(status: PageStatus): View {
        when (status) {
            PageStatus.LOADING -> return loadingView
            PageStatus.EMPTY -> return emptyView
            PageStatus.ERROR -> return errorView
        }
    }
}
package com.caldremch.widget.page

import android.view.View

/**
 * @author Caldremch
 * @date  2020/7/9
 * @email caldremch@163.com
 * @describe
 * 1.空
 * 2.错误
 * 3.加载
 *
 **/
interface IPageStatus {
    fun setRetryListener(retryListenerListener: IRetryListener?)
    fun startLoading()
    fun stopLoading()
    fun statusView(status: PageStatus): View //根据当前不同的状态, 返回不同的 View
}
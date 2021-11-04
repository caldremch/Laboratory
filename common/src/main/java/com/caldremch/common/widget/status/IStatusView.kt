package com.caldremch.common.widget.status

import android.view.View
import com.caldremch.common.widget.status.StatusView.ViewState

/**
 * @author Caldremch
 * @date 2019-08-23 10:19
 * @email caldremch@163.com
 * @describe
 */
interface IStatusView {
    /**
     * 初始化ErrorView
     *
     * @return
     */
    fun initErrorView(): View? {
        return null
    }

    /**
     * 初始化LoadingView
     *
     * @return
     */
    fun initLoadingView(): View? {
        return null
    }

    /**
     * 开始加载
     */
    fun startLoading() {}

    /**
     * 停止加载
     */
    fun stopLoading() {}

    /**
     * 获取错误View
     *
     * @return
     */
    val errorView: View?
        get() = null

    /**
     * 错误页面重试
     */
    fun reTry() {}

    //是否使用沉浸式
    fun isUseStatusBar(): Boolean = false

    //是否使用 EventBus
    fun isUseEvent(): Boolean = false

    /**
     * 是否使用加载状态也
     */
    fun isUseLoading(): Boolean = false


    fun setViewStatus(@ViewState status: Int)
}
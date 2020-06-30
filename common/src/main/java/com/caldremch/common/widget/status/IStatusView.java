package com.caldremch.common.widget.status;

import android.view.View;

/**
 * @author Caldremch
 * @date 2019-08-23 10:19
 * @email caldremch@163.com
 * @describe
 **/
public interface IStatusView {

    /**
     * 初始化ErrorView
     *
     * @return
     */
    default View initErrorView() {
        return null;
    }

    /**
     * 初始化LoadingView
     *
     * @return
     */
    default View initLoadingView() {
        return null;
    }

    /**
     * 开始加载
     */
    default void startLoading() {
    }

    /**
     * 停止加载
     */
    default void stopLoading() {
    }

    /**
     * 获取错误View
     *
     * @return
     */
    default View getErrorView() {
        return null;
    }

    /**
     * 错误页面重试
     */
    default void reTry() {
    }

    void setViewStatus(@StatusView.ViewState int status);

}

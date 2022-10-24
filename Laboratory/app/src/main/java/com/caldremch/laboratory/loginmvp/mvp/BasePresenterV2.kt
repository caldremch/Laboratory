package com.caldremch.laboratory.loginmvp.mvp


import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.caldremch.common.widget.status.IStatusView
import com.caldremch.common.widget.status.StatusView

/**
 *
 * @author Caldremch
 * @date 2019/1/24
 * @Email caldremch@163.com
 * @describe
 *
 * 如何解决mView内存泄漏的问题?
 *
 * 1. 弱引用
 * 2.生命周期管理取消网络请求, 就不会回调到页面
 */
open class BasePresenterV2<T : BaseContractV2.BaseViewV2, M : BaseContractV2.BaseModelV2>(
    protected var mView: T,
    protected var mModel: M
) : BaseContractV2.BasePresenterV2<T>, DefaultLifecycleObserver {

    init {
        //处理网络请求的取消
        val v = mView
        if (v is LifecycleOwner) {
            v.lifecycle.addObserver(this)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {

    }

    /**
     * 设置正常页面状态
     */
    protected fun setContentViewStatus() {
        if (mView is IStatusView) {
//            (mView as IStatusView).setViewStatus(StatusView.VIEW_STATE_CONTENT)
        }
    }

    /**
     * 设置错误页面状态
     */
    protected fun setErrorViewStatus() {
        if (mView is IStatusView) {
//            (mView as IStatusView).setViewStatus(StatusView.VIEW_STATE_ERROR)
        }
    }

    override fun attachView(view: T) {
        this.mView = view
    }

    override fun detachView() {
    }
}

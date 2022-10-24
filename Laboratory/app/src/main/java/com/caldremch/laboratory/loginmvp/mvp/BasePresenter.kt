package com.caldremch.laboratory.loginmvp.mvp


import android.content.Context
import com.caldremch.common.widget.status.IStatusView
import com.caldremch.common.widget.status.StatusView

/**
 *
 * @author Caldremch
 * @date 2019/1/24
 * @Email caldremch@163.com
 * @describe
 */
open class BasePresenter<T : BaseContract.BaseView> : BaseContract.BasePresenter<T> {

    var mView: T? = null

    val mContext: Context
        get() = mView!!.mContext

    /**
     * 设置正常页面状态
     */
    protected fun setContentViewStatus() {
        if (mView is IStatusView) {
//            (mView as IStatusView).setViewStatus(VIEW_STATE_CONTENT)
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
        mView = null
    }
}

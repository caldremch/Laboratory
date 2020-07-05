package com.caldremch.common.base

import android.content.Context
import android.os.Bundle
import com.caldremch.common.mvp.BaseContract
import com.caldremch.common.mvp.BasePresenter
import com.caldremch.common.util.Utils

abstract class BaseActivity<T> : AbsActivity(), BaseContract.BaseView {

    override val mContext: Context
        get() = this

    protected var mPresenter: T? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = createPresent()
        attachView()
        super.onCreate(savedInstanceState)
    }

    protected fun createPresent(): T? {
        return Utils.getInstance<T>(this)
    }

    private fun attachView() {
        if (mPresenter != null) {
            if (mPresenter is BaseContract.BasePresenter<*>) {
                (mPresenter as BasePresenter<BaseContract.BaseView>).attachView(this)
            }
        }
    }

    private fun detachView() {
        if (mPresenter != null) {
            if (mPresenter is BasePresenter<*>) {
                (mPresenter as BasePresenter<*>).detachView()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detachView()
    }


    override fun reTry() {
        if (mPresenter != null) {
            if (mPresenter is BasePresenter<*>) {
                (mPresenter as BasePresenter<*>).retry()
            }
        }
    }

}
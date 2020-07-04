package com.caldremch.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.caldremch.common.helper.EventManager
import com.caldremch.common.mvp.BaseContract
import com.caldremch.common.mvp.BaseContract.BasePresenter
import com.caldremch.common.util.Utils
import com.caldremch.common.widget.status.IStatusView
import com.caldremch.common.widget.status.StatusView


/**
 * @author Caldremch
 * @date 2018/11/9
 * @email caldremch@163.com
 * @describe 基类
 */
abstract class BaseFragment<T> : Fragment(), BaseContract.BaseView, BaseInit, IStatusView {

    private val TAG = "BaseFragment"
    protected var mPresenter: T? = null
    protected var mIsVisible = false
    private var mIsPrepare = false
    private var mIsFirst = true
    private lateinit var mContentView: View
    protected lateinit var decorViewProxy: DecorViewProxy

    override val mContext: Context
        get() = context!!

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            //JLog.d(this.getClass().getSimpleName()+"-->可见:"+getUserVisibleHint());
            mIsVisible = true
            onVisible()
            loadData()
        } else {
            mIsVisible = false
            onInvisible()
        }
    }

    protected fun onInvisible() {

    }

    protected fun onVisible() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setViewStatus(@StatusView.ViewState status: Int) {
        if (mContentView != null && mContentView is IStatusView) {
            (mContentView as IStatusView).setViewStatus(status)
        }
    }


    //主要用于ViewPage中, 否则无效
    protected fun lazyLoad() {}

    protected fun createPresenter(): T? {
        return Utils.getInstance<T>(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPresenter = Utils.getInstance<T>(this)
        attachView()
        inflaterView(inflater, container)
        return mContentView
    }

    private fun inflaterView(inflater: LayoutInflater, container: ViewGroup?) {
        decorViewProxy = DecorViewProxy.Builder(this, inflater, container)
            .setContentViewId(layoutId)
            .setTitleViewId(titleViewId)
            .setTitleView(titleView)
            .build()
        mContentView = decorViewProxy.proxySetContentView()
    }

    fun beforeInitView() {

    }


    protected fun <K : View> findViewById(@IdRes id: Int): K {
        return mContentView!!.findViewById(id)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isUseEvent()) {
            EventManager.register(this)
        }
        beforeInitView()
        initView()
        initData()
        initEvent()
        mIsPrepare = true
        mIsFirst = true

        loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setContentStatus()//用于取消 状态页面的loading
        if (isUseEvent()) {
            EventManager.unregister(this)
        }
        detachView()
    }

    //加载数据
    protected fun loadData() {
        if (!mIsPrepare || !mIsVisible || !mIsFirst) {
            return
        }
        lazyLoad()
        mIsFirst = false
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private fun attachView() {
        if (mPresenter != null) {
            if (mPresenter is BasePresenter<*>) {
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

    //如果Fragment中有present, 则可以重写present里面的retry方法, 不然, 则重写Fragment的retry
    override fun reTry() {
        if (mPresenter != null) {
            if (mPresenter is BasePresenter<*>) {
                (mPresenter as BasePresenter<*>).retry()
            }
        }
    }


    protected fun setErrorStatus() {
        setViewStatus(StatusView.VIEW_STATE_ERROR)
    }

    protected fun setContentStatus() {
        setViewStatus(StatusView.VIEW_STATE_CONTENT)
    }
}

package com.caldremch.common.base

import android.os.Bundle
import android.view.ViewGroup
import com.caldremch.common.helper.EventManager
import com.caldremch.common.helper.StatusBarManager
import com.caldremch.common.widget.status.IStatusView

/**
 *
 * @author Caldremch
 *
 * @date 2020-06-28 22:51
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
open class AbsActivity : LifeCycleLogActivity(), BaseInit, IStatusView {

    protected var statusBarManager: StatusBarManager? = null
    private lateinit var contentView: ViewGroup
    private lateinit var contentViewDelegate: DecorViewDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isUseEvent()) {
            EventManager.register(this)
        }
        if (isUseStatusBar()) {
            statusBarManager = StatusBarManager(this)
        }
        contentViewDelegate = DecorViewDelegate(this, isUseLoading(), layoutId, titleViewId)
        contentView = contentViewDelegate.proxySetContentView()
        initView()
        initData()
        initEvent()
    }

    protected fun isUseStatusBar(): Boolean = false
    protected fun isUseEvent(): Boolean = false
    protected fun isUseLoading(): Boolean = false

    override fun setViewStatus(status: Int) {
        contentViewDelegate.setViewStatus(status)
    }

}


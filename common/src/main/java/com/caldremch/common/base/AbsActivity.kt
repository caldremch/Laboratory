package com.caldremch.common.base

import android.os.Bundle
import android.view.View
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
    private lateinit var mContentView: View
    private lateinit var contentViewDelegate: DecorViewProxy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isUseEvent()) {
            EventManager.register(this)
        }
        if (isUseStatusBar()) {
            statusBarManager = StatusBarManager(this)
        }
        contentViewDelegate = DecorViewProxy.Builder(this)
            .isUseLoading(isUseLoading()).setContentViewId(layoutId).setTitleViewId(titleViewId)
            .build()
        mContentView = contentViewDelegate.proxySetContentView()
        setContentView(mContentView)
        initView()
        initData()
        initEvent()
    }

    override fun setViewStatus(status: Int) {
//        contentViewDelegate.setViewStatus(status)
    }

}


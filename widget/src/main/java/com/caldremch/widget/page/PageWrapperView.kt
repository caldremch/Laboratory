package com.caldremch.widget.page

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * @author Caldremch
 * @date  2020/7/9
 * @email caldremch@163.com
 * @describe
 * 1.有加载loading
 * 2.设置空页面
 * 3.单独设置Adapter
 * 4.设置Layoutmanager
 * 5.设置设置ItemDecorater
 *
 **/
abstract class PageWrapperView<T>(var mContext: Context, var loadingEnable: Boolean = true) :
    SmartRefreshLayout(mContext), ICustomerConfig<T> {

    private lateinit var mRv: RecyclerView
    private lateinit var mRootView: ViewGroup
    private lateinit var mAdapter: BaseQuickAdapter<T, BaseViewHolder>
    private val mCurrentPageIndex = 1
    private val mPageSize: Int = 20

    init {
        initWrapperView()
        initLoad()
        initRvConfig()
        initAdapterConfig()
    }

    private fun initLoad() {
        if (loadingEnable) {
            getLoading()?.startLoading()
        } else {
            getLoading()?.apply {
                mRootView.removeView(loadingView())
            }
        }
    }

    private fun initAdapterConfig() {
        loadingEnable = false
        mRv.adapter = getAdapter()
    }

    private fun initRvConfig() {
        mRv.layoutManager = getLayoutManager()
    }

    private fun initWrapperView() {
        //为了空布局的时候, 也可以下拉刷新
        mRootView = FrameLayout(context)
        mRootView.layoutParams = createLayoutParams()
        addView(mRootView)
        getLoading()?.apply {
            //todo loadingView 位置是否需要设置?
            val params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
                dp2px(30),
                dp2px(30)
            )
            params.gravity = Gravity.CENTER
            mRootView.addView(loadingView())
        }
        mRv = RecyclerView(context)
        mRv.layoutParams = createLayoutParams()

    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }

    private fun createLayoutParams(): FrameLayout.LayoutParams {
        return FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }

    open fun dp2px(dpValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    //abs
    abstract fun getLoading(): IPageLoading?

    //default empty, error View
    abstract fun getStatusView(): View

    //default footer view
    abstract fun getFooterView(): View

}
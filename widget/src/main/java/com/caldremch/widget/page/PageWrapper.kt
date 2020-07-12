package com.caldremch.widget.page

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.widget.BuildConfig
import com.caldremch.widget.page.protocol.*
import com.caldremch.widget.page.view.WrapRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.lang.RuntimeException

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
abstract class PageWrapper<T>(
    protected var context: Context,
    val fragment: Fragment?,
    var pageDelegate: IPageDelegate<T>,
    private var loadingEnable: Boolean
) : LifecycleObserver, IPageOperator<T> {

    private lateinit var mRv: RecyclerView
    private lateinit var mRootView: ViewGroup
    private lateinit var mAdapter: BaseQuickAdapter<T, BaseViewHolder>
    private var mCurrentPageIndex = 1
    private val mPageSize: Int = pageDelegate.getPageSize()
    private lateinit var refreshHandle: IRefresh

    //    private var loadingHandle:IPageLoading? = null
    private var pageStatusHandle: IPageStatus? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        if (BuildConfig.DEBUG) {
            Log.d("TAG", "onDestroy: ")
        }
        refreshHandle.onFinishRefreshAndLoadMore()
        stopLoading()
    }

    init {
        initObs()
        initWrapperView()
        initPageStatus()
        initAdapterConfig()
        initRvConfig()
        initEvent()
    }

    private fun initEvent() {
        //点击事件
        mAdapter.setOnItemClickListener { adp: BaseQuickAdapter<*, *>, view: View, position: Int ->
            //添加安全检查
            if (position < adp.data.size) {
                pageDelegate.handleItemClick(mAdapter.data[position], view, adp, position)
            }
        }

        //子View点击事件
        mAdapter.setOnItemChildClickListener { adp: BaseQuickAdapter<*, *>, view: View?, position: Int ->
            pageDelegate.handleItemChildClick(mAdapter.data[position], view, adp, position)
        }

        //上拉加载更多
        refreshHandle.setOnLoadMoreListenerEx(OnLoadMoreListenerEx {
            mCurrentPageIndex++
            pageDelegate.getData(mCurrentPageIndex)
        })


        //下拉刷新
        refreshHandle.setOnRefreshListener(OnRefreshListenerEx {
            refresh()
        })
    }

    override fun handleData(data: List<T>?) {

        refreshHandle.onFinishRefreshAndLoadMore()

        if (data != null) {
            if (mCurrentPageIndex == 1) {
                mAdapter.data.clear()
            }
            mAdapter.data.addAll(data)


            //每次加载 pageSize, 如果pageData.items 返回的数目等于 pageSize, 则可以继续上拉加载
            //判断是否可以上拉加载
            if (data.size == mPageSize) {
                refreshHandle.setEnableLoadMoreEx(true)
                //                if (mAdapter.getFooterLayoutCount() > 0) {
//                    mAdapter.removeAllFooterView();
//                }
            } else {
                refreshHandle.setEnableLoadMoreEx(false)
                //                if (mAdapter.getFooterLayoutCount() == 0 && !mListData.isEmpty()) {
//                    mAdapter.addFooterView(mFooterView);
//                }
            }
            mAdapter.notifyDataSetChanged()
        }

        if (mCurrentPageIndex <= 1) {
            if (data == null) {
                //当页面中有数据, 再刷新一次(请求第一页), 返回pageData == null, 则覆盖原有的数据源
                mAdapter.setList(null)
            }
        }

        if (mCurrentPageIndex > 1 && data == null) {
            refreshHandle.setEnableLoadMoreEx(false)
//            if (mAdapter.getFooterLayoutCount() == 0 && !mListData.isEmpty()) {
//                mAdapter.addFooterView(mFooterView);
//            }
        }

        //停止动画[如果有]
        stopLoading()

        //设置空状态
        if (mAdapter.data.isEmpty()) {
            //刷新能知道空状态, 但是如果列表有删除操作, 当删除结束后, 并不知道当前的数据已经被清空, 所以需要设置到 adapter 里面
            pageStatusHandle?.apply {
                mAdapter.setEmptyView(statusView(PageStatus.EMPTY))
            }
        }

        hideErrorView()
    }

    override fun handleError() {
        refreshHandle.onFinishRefreshAndLoadMore()
        if (mCurrentPageIndex > 1) {
            mCurrentPageIndex--
        }
        stopLoading()
        showErrorView()
    }

    private fun showErrorView() {
        //设置错误状态, 只有首次进入的时候, 失败的是的时候, 如果已经有数据了, 是不会显示的
        if (mAdapter.data.isEmpty()) {
            pageStatusHandle?.apply {
                mRootView.addView(statusView(PageStatus.ERROR))
            }
        }
    }

    private fun hideErrorView() {
        pageStatusHandle?.apply {
            mRootView.removeView(statusView(PageStatus.ERROR))
        }
    }


    override fun refresh() {
        mCurrentPageIndex = 1
        pageDelegate.getData(mCurrentPageIndex)
    }

    private fun initObs() {

        if (fragment is LifecycleOwner) {
            val owner: LifecycleOwner = fragment as LifecycleOwner
            owner.lifecycle.addObserver(this)
        } else if (context is LifecycleOwner) {
            val owner: LifecycleOwner = context as LifecycleOwner
            owner.lifecycle.addObserver(this)
        }
    }

    private fun initPageStatus() {
        pageStatusHandle = getStatusView()
        pageStatusHandle?.setRetryListener(IRetryListener {
            //重试的时候,将错误页面移除
            hideErrorView()
            startLoading()
            refresh()
        })
        startLoading()
    }

    private fun startLoading() {
        if (loadingEnable) {
            pageStatusHandle?.apply {
                val params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
                    dp2px(30),
                    dp2px(30)
                )
                params.gravity = Gravity.CENTER
                mRootView.addView(statusView(PageStatus.LOADING), params)
                startLoading()
            }
        }
    }

    private fun stopLoading() {
        if (loadingEnable) {
            pageStatusHandle?.apply {
                stopLoading()
                mRootView.removeView(statusView(PageStatus.LOADING))
            }
        }
    }

    private fun initAdapterConfig() {
        mAdapter = pageDelegate.getAdapter() ?: getDefaultAdapter()
    }

    private fun initRvConfig() {
        mRv.layoutManager = pageDelegate.getLayoutManager() ?: getDefaultLayoutManager()
        pageDelegate.getItemDecoration()?.apply {
            mRv.addItemDecoration(this)
        }
        mRv.adapter = mAdapter
        mRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            }
        })
    }

    private fun initWrapperView() {

        refreshHandle = WrapRefreshLayout(context)
        //为了空布局的时候, 也可以下拉刷新
        mRootView = FrameLayout(context)
        mRootView.layoutParams = createLayoutParams()
        refreshHandle.getView().addView(mRootView)
        mRv = RecyclerView(context)
        mRootView.addView(mRv, createLayoutParams())
    }

    private fun showEmptyView() {
        pageStatusHandle?.apply {
            mRootView.addView(statusView(PageStatus.EMPTY), createLayoutParams())
        }
    }

    private fun hideEmptyView() {
        pageStatusHandle?.apply {
            mRootView.removeView(statusView(PageStatus.EMPTY))
        }
    }

    //使用默认方式设置adapter
    private fun getDefaultAdapter(): BaseQuickAdapter<T, BaseViewHolder> {
        pageDelegate.getItemLayoutId() ?: throw RuntimeException("getItemLayoutId can't be null")
        return object :
            BaseQuickAdapter<T, BaseViewHolder>(pageDelegate.getItemLayoutId()!!, arrayListOf()) {
            override fun convert(holder: BaseViewHolder, item: T) {
                pageDelegate.setItemView(holder, item)
            }
        }
    }

    private fun getDefaultLayoutManager(): RecyclerView.LayoutManager {
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

    //default empty, error View
    abstract fun getStatusView(): IPageStatus?

    //default footer view
//    abstract fun getFooterView(): View

    override fun getPageView(): View {
        return refreshHandle.getView()
    }
}
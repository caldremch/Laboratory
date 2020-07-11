package com.caldremch.widget.page

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.widget.page.base.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

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
abstract open class PageWrapper<T>(
    protected var context: Context,
    var pageDelegate: IPageDelegate<T>,
    var loadingEnable: Boolean
) :  IPageOperator<T>, LifecycleObserver {

    private lateinit var mRv: RecyclerView
    private lateinit var mRootView: ViewGroup
    private lateinit var mAdapter: BaseQuickAdapter<T, BaseViewHolder>
    private var mCurrentPageIndex = 1
    private val mPageSize: Int = 20
    private lateinit var refreshHandle: IRefresh

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        getLoading()?.stopLoading()
    }

    init {
        initObs()
        initWrapperView()
        initLoad()
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
        mAdapter.setOnItemChildClickListener { adp: BaseQuickAdapter<*, *>?, view: View?, position: Int ->
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
    }

    override fun handleError() {
        if (mCurrentPageIndex > 1) {
            mCurrentPageIndex--
        }
    }


    open fun refresh() {
        mCurrentPageIndex = 1
        pageDelegate.getData(mCurrentPageIndex)
    }

    private fun initObs() {
        if (context is LifecycleOwner) {
            val owner: LifecycleOwner = context as LifecycleOwner
            owner.lifecycle.addObserver(this)
        }
    }


    private fun initLoad() {
        if (loadingEnable) {
            getLoading()?.apply {
                //todo loadingView 位置是否需要设置?
                val params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
                    dp2px(30),
                    dp2px(30)
                )
                params.gravity = Gravity.CENTER
                mRootView.addView(loadingView(), params)
            }
            getLoading()?.startLoading()
        }
//        else {
//            getLoading()?.apply {
//                mRootView.removeView(loadingView())
//            }
//        }
    }

    private fun initAdapterConfig() {
        loadingEnable = false
        mAdapter = getAdapter()
    }

    private fun initRvConfig() {
        mRv.layoutManager = getLayoutManager()
        getItemDecoration()?.apply {
            mRv.addItemDecoration(this)
        }
        mRv.adapter = mAdapter
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
        getStatusView()?.apply {
            mRootView.addView(this, createLayoutParams())
        }
    }

    private fun hideEmptyView() {
        getStatusView()?.apply {
            mRootView.removeView(this)
        }
    }

    //使用默认方式设置adapter
    override fun getAdapter(): BaseQuickAdapter<T, BaseViewHolder> {
        return object : BaseQuickAdapter<T, BaseViewHolder>(getItemLayoutId(), arrayListOf()) {
            override fun convert(holder: BaseViewHolder, item: T) {
                pageDelegate.setItemView(holder, item)
            }
        }
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

    override fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    override fun getItemLayoutId(): Int {
        return pageDelegate.getItemLayoutId()
    }

    //abs
    abstract fun getLoading(): IPageLoading?

    //default empty, error View
    abstract fun getStatusView(): View?

    //default footer view
//    abstract fun getFooterView(): View

    override fun getPageView(): View {
        return refreshHandle.getView()
    }
}
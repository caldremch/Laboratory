package com.caldremch.widget.page

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author Caldremch
 * @date  2020/7/9
 * @email caldremch@163.com
 * @describe 自定义设置
 *
 **/
interface ICustomerConfig<T> {
    fun getLayoutManager(): RecyclerView.LayoutManager
    fun getItemDecoration(): RecyclerView.ItemDecoration?
    fun getAdapter(): BaseQuickAdapter<T, BaseViewHolder>
    fun getItemLayoutId(): Int
    fun getPageView(): View
}
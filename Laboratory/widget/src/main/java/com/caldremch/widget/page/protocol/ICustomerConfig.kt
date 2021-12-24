package com.caldremch.widget.page.protocol

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
    fun getLayoutManager(): RecyclerView.LayoutManager?{
        return null
    }

    fun getItemDecoration(): RecyclerView.ItemDecoration?{
        return null
    }

    fun getAdapter(): BaseQuickAdapter<T, BaseViewHolder>?{
        return null
    }

    fun getItemLayoutId(): Int? {
        return null
    }

    //默认 20
    fun getPageSize():Int{
        return 20
    }
}
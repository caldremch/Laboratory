package com.caldremch.widget.page.protocol

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author Caldremch
 * @date 2019-07-04 10:16
 * @email caldremch@163.com
 * @describe 分页数据处理 ,代理控制者进行操作
 */
interface IPageDelegate<T> : ICustomerConfig<T> {

    /*数据与view*/
    fun setItemView(holder: BaseViewHolder, item: T) {}

    /**
     * 获取 item 布局, 可以不传, 当不使用默认的 adapter 时
     */

    /**
     * item 点击
     */
    fun handleItemClick(
        item: T,
        view: View,
        adapter: BaseQuickAdapter<*, *>,
        position: Int
    ) {
    }

    /**
     * 子view item 点击
     */
    fun handleItemChildClick(
        item: T,
        view: View?,
        adapter: BaseQuickAdapter<*, *>,
        position: Int
    ) {
    }

    /**
     * 获取列表数据
     *
     * @param pageIndex 当前页
     */
    fun getData(pageIndex: Int)
}
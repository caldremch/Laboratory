package com.caldremch.widget.page;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


/**
 * @author Caldremch
 * @date 2019-07-04 10:16
 * @email caldremch@163.com
 * @describe 分页数据处理 ,代理控制者进行操作
 **/
public interface IPageDelegate<T> {

    /*数据与view*/
    default void setItemView(BaseViewHolder holder, T item) {
    }

    /**
     * 获取 item 布局, 可以不传, 当不使用默认的 adapter 时
     */
    default int getItemLayoutId() {
        return 0;
    }

    /**
     * item 点击
     */
    default void handleItemClick(T item, View view, BaseQuickAdapter adapter, int position) {

    }

    /**
     * 子view item 点击
     */
    default void handleItemChildClick(T item, View view, BaseQuickAdapter adapter, int position) {

    }

    /**
     * 获取列表数据
     *
     * @param pageIndex 当前页
     */
    void getData(int pageIndex);

    /**
     * 用于装载RecyclerViewWrap的 view id
     * @return
     */
//    default int getListContainerId(){
//        return R.id.listContainer;
//    }

    /**
     * 创建 adapter
     * @return
     */
//    default BaseQuickAdapter<T, BaseViewHolder> createAdapter(){
//return  null;
//    }
}

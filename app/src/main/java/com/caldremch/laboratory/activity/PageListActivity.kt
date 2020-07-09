package com.caldremch.laboratory.activity

import com.caldremch.common.base.BaseActivity
import com.caldremch.laboratory.R
import com.caldremch.laboratory.page.TestData
import com.caldremch.widget.page.IPageDelegate
import com.caldremch.widget.page.PageWrapperView
import kotlinx.android.synthetic.main.activity_page_list.*

/**
 * @author Caldremch
 * @date  2020/7/9
 * @email caldremch@163.com
 * @describe
 *
 **/
class PageListActivity : BaseActivity<Any>(),IPageDelegate<TestData> {

    override fun getLayoutId(): Int {
        return R.layout.activity_page_list
    }

    override fun initView() {
        rootView.addView(PageWrapperView<TestData>(this, this))
    }

    override fun getData(pageIndex: Int) {

    }
}
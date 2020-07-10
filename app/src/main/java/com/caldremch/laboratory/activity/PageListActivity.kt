package com.caldremch.laboratory.activity

import android.view.ViewGroup
import android.widget.FrameLayout
import com.caldremch.common.base.BaseActivity
import com.caldremch.laboratory.R
import com.caldremch.laboratory.page.TestData
import com.caldremch.widget.page.IPageDelegate
import com.caldremch.widget.page.PageManager
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
        val pageManager = PageManager.Builder<TestData>(this).build()
        rootView.addView(
            pageManager.getPageView(),
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    override fun getData(pageIndex: Int) {

    }
}
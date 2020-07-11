package com.caldremch.laboratory.activity

import android.os.Handler
import android.view.ViewGroup
import android.widget.FrameLayout
import com.caldremch.common.base.BaseActivity
import com.caldremch.laboratory.R
import com.caldremch.laboratory.page.TestData
import com.caldremch.widget.page.IPageDelegate
import com.caldremch.widget.page.PageManager
import com.caldremch.widget.page.base.IPageOperator
import kotlinx.android.synthetic.main.activity_page_list.*

/**
 * @author Caldremch
 * @date  2020/7/9
 * @email caldremch@163.com
 * @describe
 *
 **/
class PageListActivity : BaseActivity<Any>(), IPageDelegate<TestData> {

    private lateinit var pageManager: IPageOperator<TestData>

    override fun getLayoutId(): Int {
        return R.layout.activity_page_list
    }

    override fun initView() {
        pageManager = PageManager.Builder<TestData>(this)
            .setLoadEnable(false)
            .build()

        rootView.addView(
            pageManager.getPageView(),
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        //模拟网路请求
        Handler().postDelayed(Runnable {
            val list = arrayListOf<TestData>()
            for (x in 0 until 10) {
                val testData = TestData()
                list.add(testData)
            }
            pageManager.handleData(list)
        }, 2000)

    }

    override fun getData(pageIndex: Int) {

    }

    override fun getItemLayoutId(): Int {
        return R.layout.item_test
    }
}
package com.caldremch.laboratory.fragment

import android.os.Handler
import android.view.ViewGroup
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.laboratory.bean.TestData
import com.caldremch.laboratory.databinding.ActivityPageListBinding
import com.caldremch.widget.page.PageManager
import com.caldremch.widget.page.protocol.IPageDelegate
import com.caldremch.widget.page.protocol.IPageOperator
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-12 15:12
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class PageListFragment : LaboratoryFragment(), IPageDelegate<TestData> {

    private lateinit var pageManager:IPageOperator<TestData>

    private val binding by viewBinding(ActivityPageListBinding::bind)

    override fun getTitle(): String {
        return "分页demo Fragment"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_page_list
    }

    override fun initView() {

        pageManager = PageManager.Builder<TestData>(this)
            .setLoadEnable(true)
            .build()

        binding.rootView.addView(
            pageManager.getPageView(),
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

//        setError()
    }

    override fun getData(pageIndex: Int) {
//        setData()
    }

    override fun getAdapter(): BaseQuickAdapter<TestData, BaseViewHolder>? {
        return object: BaseQuickAdapter<TestData, BaseViewHolder>(R.layout.item_test){
            override fun convert(holder: BaseViewHolder, item: TestData) {
                holder.setText(R.id.tv, item.title)
            }

        }
    }

    private fun setError() {
        Handler().postDelayed(Runnable {
            pageManager.handleError()
        }, 2000)
    }

    private fun setEmpty() {
        Handler().postDelayed(Runnable {
            pageManager.handleData(null)
        }, 2000)
    }

    private fun setData() {
        Handler().postDelayed(Runnable {
            val list = arrayListOf<TestData>()
            for (x in 0 until 10) {
                val testData = TestData()
                testData.title = "标题$x"
                list.add(testData)
            }
            //            pageManager.handleData(list)
            pageManager.handleData(list)
        }, 2000)
    }
}
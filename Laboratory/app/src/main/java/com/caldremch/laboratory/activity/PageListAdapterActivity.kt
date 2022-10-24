package com.caldremch.laboratory.activity

import android.graphics.Rect
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.common.base.AbsActivity
import com.caldremch.laboratory.R
import com.caldremch.laboratory.bean.TestData
import com.caldremch.laboratory.databinding.ActivityPageListBinding
import com.caldremch.widget.page.PageManager
import com.caldremch.widget.page.protocol.IPageDelegate
import com.caldremch.widget.page.protocol.IPageOperator
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author Caldremch
 * @date  2020/7/9
 * @email caldremch@163.com
 * @describe
 *
 **/
class PageListAdapterActivity : AbsActivity(),
    IPageDelegate<TestData> {

    private val binding by viewBinding(ActivityPageListBinding::bind)

    private lateinit var pageManager: IPageOperator<TestData>

    override val layoutId: Int
        get() = R.layout.activity_page_list

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

        //获取第一页数据
        setError()
    }

    override fun getData(pageIndex: Int) {
        //模拟网路请求
        setData()
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
                testData.title = "自己创建adapter-标题$x"
                list.add(testData)
            }
            //            pageManager.handleData(list)
            pageManager.handleData(list)
        }, 2000)
    }

    override fun getAdapter(): BaseQuickAdapter<TestData, BaseViewHolder>? {
        return object: BaseQuickAdapter<TestData, BaseViewHolder>(R.layout.item_test){
            override fun convert(holder: BaseViewHolder, item: TestData) {
                holder.setText(R.id.tv, item.title)
            }

        }
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = 20
            }
        }
    }

}
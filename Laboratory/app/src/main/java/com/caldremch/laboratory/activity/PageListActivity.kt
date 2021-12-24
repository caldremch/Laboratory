package com.caldremch.laboratory.activity

import android.app.ActivityManager
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.common.base.BaseActivity
import com.caldremch.common.helper.EventManager
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
class PageListActivity : BaseActivity<Any>(),
    IPageDelegate<TestData> {

    private val TAG = "PageListActivity"

    private lateinit var pageManager: IPageOperator<TestData>

    private val binding by viewBinding(ActivityPageListBinding::bind)

    override fun getLayoutId(): Int {
        return R.layout.activity_page_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        Log.d(TAG, "onCreate: ${activityManager.appTasks.size}")
        activityManager.appTasks.forEach {
            Log.d(TAG, "onCreate: ${it.taskInfo.taskId}")
        }
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

        //获取第一页数据
        setError()
    }


    override fun getData(pageIndex: Int) {
        //模拟网路请求
        setData(pageIndex)
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

    var start = 0
    private fun setData(pageIndex: Int) {
        if (pageIndex == 1){
            start = 0
        }else{
            start += getPageSize()
        }
        Handler().postDelayed(Runnable {
            val list = arrayListOf<TestData>()
            for (x in start until (start+20) ) {
                val testData = TestData()
                testData.title = "标题$x"
                list.add(testData)
            }
            //            pageManager.handleData(list)
            pageManager.handleData(list)
        }, 2000)
    }

    override fun getItemLayoutId(): Int? {
        return R.layout.item_test
    }

    override fun setItemView(holder: BaseViewHolder, item: TestData) {
        holder.setText(R.id.tv, item.title)
    }

    override fun handleItemClick(
        item: TestData,
        view: View,
        adapter: BaseQuickAdapter<*, *>,
        position: Int
    ) {
        EventManager.post(OpenActEvent())
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
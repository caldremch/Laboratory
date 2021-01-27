package com.caldremch.laboratory.fragment

import android.view.View
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.laboratory.widget.SearchHistoryFlowLayout

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/1/27 09:40
 *
 * @description
 *
 *
 */
class SearchViewFragment : LaboratoryFragment() {

    //    val binding  by viewBinding
//    val binding by viewBinding()
//    private val viewBinding by viewBinding()
    private lateinit var searchList: SearchHistoryFlowLayout
    private lateinit var title: View

    override fun getLayoutId(): Int {
        return R.layout.house_item_history_tags
    }

    override fun initView() {
        searchList = findViewById(R.id.tfl_history_tags)
        title = findViewById(R.id.tv_history_str)


        val list = arrayListOf<String>(
                "11",
                "22",
                "33",
                "44",
                "好3333",
                "好3333",
                "55",
                "66",
                "77-",
                "88,1",
                "88,2",
                "88,3",
                "88,412345678902345",
                "88,5",
                "6",
                "7",
                "88,8",
                "88,9",
                "99-1234567890123567890123567890123",
                "12345678901234567890-34567890123456789012345678908901234567890",
                "12345678901234567890-34567890"
        )

        //测量相册

        searchList.setList(list)
    }

//    override fun getLayoutView(): View {
//        val root = FrameLayout(requireContext())
//        val container = View.inflate(requireContext(), , root)
//        root.addView(container)
//        return root
//    }
}
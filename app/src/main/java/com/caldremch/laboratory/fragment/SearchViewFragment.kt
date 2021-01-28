package com.caldremch.laboratory.fragment

import android.view.View
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.laboratory.widget.FlowLayout

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
    private lateinit var searchList: FlowLayout
    private lateinit var title: View

    override fun getLayoutId(): Int {
        return R.layout.house_item_history_tags
    }

    override fun initView() {
        searchList = findViewById(R.id.tfl_history_tags)
        title = findViewById(R.id.tv_history_str)
        val list = getList(1)
        //测量相册
        title.setOnClickListener {
            val testDataIndex = java.util.Random().nextInt(3)
            val testList = getList(testDataIndex)
            searchList.setList(testList)
        }
        searchList.setList(list)
    }

    private fun getList(index: Int): List<String> {
        return when (index) {
            0 -> arrayListOf<String>(
                    "11",
                    "22",
                    "33",
                    "44",
                    "好3333",
                    "好3333",
                    "55",
                    "66",
                    "7",
                    "8",
                    "88,2",
                    "88,3",
                    "88,5",
                    "6",
                    "7",
                    "7",
                    "7",
                    "11",
                    "11",
                    "88,8",
                    "88,9",
                    "99-1234567890123567890123567890123",
                    "12345678901234567890-34567890123456789012345678908901234567890",
                    "12345678901234567890-34567890"
            )
            1 -> arrayListOf<String>(
                    "11",
                    "22",
                    "33",
                    "44",
                    "好3333",
                    "好3333",
                    "55",
                    "66",
                    "7",
                    "8",
                    "88,2",
                    "88,3",
                    "88,5",
                    "6",
                    "7",
                    "7",
                    "7",
                    "1",
                    "1",
                    "88,8",
                    "88,9",
                    "99-1234567890123567890123567890123",
                    "12345678901234567890-34567890123456789012345678908901234567890",
                    "12345678901234567890-34567890"
            )
            2 -> arrayListOf<String>(
                    "11",
                    "22",
                    "33",
                    "44",
                    "好3333",
                    "好3333",
                    "55",
                    "66",
                    "7",
                    "8",
                    "88,2",
                    "88,3",
                    "88,5",
                    "6",
                    "7",
                    "7",
                    "7",
                    "1",
                    "1",
                    "1",
                    "88,9",
                    "99-1234567890123567890123567890123",
                    "12345678901234567890-34567890123456789012345678908901234567890",
                    "12345678901234567890-34567890"
            )
            else -> arrayListOf<String>(
                    "11",
                    "22",
                    "33",
                    "44",
                    "好3333",
                    "好3333",
                    "55",
                    "66",
                    "7",
                    "8",
                    "88,2",
                    "88,3",
                    "88,5",
                    "6",
                    "7",
                    "7",
                    "7",
                    "11",
                    "11",
                    "88,8",
                    "88,9",
                    "99-1234567890123567890123567890123",
                    "12345678901234567890-34567890123456789012345678908901234567890",
                    "12345678901234567890-34567890"
            )
        }
    }


//    override fun getLayoutView(): View {
//        val root = FrameLayout(requireContext())
//        val container = View.inflate(requireContext(), , root)
//        root.addView(container)
//        return root
//    }
}
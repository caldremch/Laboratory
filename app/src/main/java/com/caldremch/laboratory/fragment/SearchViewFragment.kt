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


    var index = 0

    override fun initView() {
        searchList = findViewById(R.id.tfl_history_tags)
        title = findViewById(R.id.tv_history_str)
        val list = getList(7)
        //测量相册
        title.setOnClickListener {
            val testDataIndex = java.util.Random().nextInt(3)
            val testList = getList(index++ % 6)
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
            3 -> arrayListOf<String>(
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
                    "88,512345678901234567890-34567890123456789012345678908901234567890",
                    "88,9",
                    "99-1234567890123567890123567890123",
                    "12345678901234567890-34567890123456789012345678908901234567890",
                    "12345678901234567890-34567890"
            )
            4 -> arrayListOf<String>(
                    "77,512345678901234567890-34567890123456789012345678908901234567890",
                    "88,512345678901234567890-34567890123456789012345678908901234567890",
                    "99,512345678901234567890-34567890123456789012345678908901234567890"
            )
            5 -> arrayListOf<String>(
                    "77,512345678901234567890-34567890123456789012345678908901234567890",
                    "88,",
                    "99,512345678901234567890-34567890123456789012345678908901234567890"
            )
            6 -> arrayListOf<String>(
                    "77,512345678901234567890-34567890123456789012345678908901234567890",
                    "88,",
                    "99,512345678901234567890-34567890123456789012345678908901234567890"
            )
            7-> arrayListOf(
                    "i你差点你才是可能性搜你那哦熟悉你哦时擦你哦熟悉呢喀纳斯休息上哪看熟悉你卡熟悉呢凯撒从哪凯撒从哪看上哪看谢娜凯撒显卡你仨",
                    "v好吧济南济南看不看得从哪开大从哪可惜嗯可惜是你你熟悉你卡熟悉你仨小腻腻等你我打你",
                    "地方烦烦烦烦烦烦烦嘎嘎嘎嘎哈哈哈急急急急急急"
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
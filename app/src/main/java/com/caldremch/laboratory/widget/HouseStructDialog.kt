package com.caldremch.laboratory.widget

import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.dialog.BaseDialog
import com.caldremch.laboratory.R

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/6/30
 *
 * @describe: 户型弹窗
 *
 * 室 max: 5
 * 厅 max: 3
 * 卫 max: 3
 *
 **/

class HouseStructDialog(parent: Any) : BaseDialog(parent) {


    private lateinit var rv_room: RecyclerView
    private lateinit var rv_hall: RecyclerView
    private lateinit var rv_toilet: RecyclerView
    private var houseStruct: HouseStruct? = null
    private lateinit var roomAdapter: HouseStructAdapter
    private lateinit var hallAdapter: HouseStructAdapter
    private lateinit var toiletAdapter: HouseStructAdapter

    //显示item数量
    private val maxRoomItem = 5
    private val maxHallItem = 4
    private val maxToiletItem = 4

    init {
        gravity = Gravity.BOTTOM
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_house_struct
    }

    override fun initView(rootView: View) {

        rv_room = rootView.findViewById(R.id.rv_room)
        rv_hall = rootView.findViewById(R.id.rv_hall)
        rv_toilet = rootView.findViewById(R.id.rv_toilet)

        rv_room.layoutManager = getLayoutManager()
        rv_hall.layoutManager = getLayoutManager()
        rv_toilet.layoutManager = getLayoutManager()

        rootView.findViewById<View>(R.id.tv_clear).setOnClickListener {
            roomAdapter.clearSelected()
            hallAdapter.clearSelected()
            toiletAdapter.clearSelected()
        }

        rootView.findViewById<View>(R.id.tv_complete).setOnClickListener {

        }
    }

    override fun initData() {
        arguments?.apply {
            val data = getSerializable("data")
            if (data != null) {
                houseStruct = data as HouseStruct
            }
            initAdapter()
        }
    }

    private fun initAdapter() {
        initAdapter(HouseStructValue.ROOM, maxRoomItem)
        initAdapter(HouseStructValue.HALL, maxHallItem)
        initAdapter(HouseStructValue.TOILET, maxToiletItem)
        initDefaultPosition()
        rv_room.adapter = roomAdapter
        rv_hall.adapter = hallAdapter
        rv_toilet.adapter = toiletAdapter
    }

    private fun initDefaultPosition() {

        houseStruct?.section?.let {
            if (it >= maxRoomItem) {
                roomAdapter.data[maxRoomItem - 1].value = it
                roomAdapter.data[maxRoomItem - 1].isSelect = true
                roomAdapter.selectedPos = maxRoomItem - 1
            } else {
                roomAdapter.data.forEachIndexed { index, houseStructValue ->
                    if (houseStructValue.value == it) {
                        roomAdapter.mDatas[index].isSelect = true
                        roomAdapter.selectedPos = index
                        return@forEachIndexed
                    }
                }
            }
        }

        sameSet(houseStruct?.hall, maxHallItem, hallAdapter)
        sameSet(houseStruct?.toilet, maxToiletItem, toiletAdapter)
    }

    private fun sameSet(currentValue: Int?, maxItemCount: Int, adapter: HouseStructAdapter) {
        currentValue?.let {
            if (it > maxItemCount) {
                adapter.data[maxItemCount - 1].value = it
                adapter.data[maxItemCount - 1].isSelect = true
                adapter.selectedPos = maxItemCount - 1
            } else {
                adapter.data.forEachIndexed { index, houseStructValue ->
                    if (houseStructValue.value == it) {
                        adapter.mDatas[index].isSelect = true
                        adapter.selectedPos = index
                        return@forEachIndexed
                    }
                }
            }
        }
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(mContext, 5)
    }

    //创建Adapter 及 选中位置指定
    private fun initAdapter(type: Int, maxItemCount: Int) {
        val roomData = mutableListOf<HouseStructValue>()
        //创建数据源
        for (i in 0 until maxItemCount) {
            val houseStructValue: HouseStructValue
            if (i == maxItemCount - 1) {
                houseStructValue = HouseStructValue(null, true)
            } else {
                houseStructValue = HouseStructValue(if (type == HouseStructValue.ROOM) i + 1 else i)
            }
            roomData.add(houseStructValue)
        }

        when (type) {
            HouseStructValue.ROOM -> {
                roomAdapter = HouseStructAdapter(roomData, rv_room, type)
            }
            HouseStructValue.HALL -> {
                hallAdapter = HouseStructAdapter(roomData, rv_hall, type)
            }
            HouseStructValue.TOILET -> {
                toiletAdapter = HouseStructAdapter(roomData, rv_toilet, type)
            }
        }
    }


}
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
    private lateinit var houseStruct: HouseStruct

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


    }

    override fun initData() {
        arguments?.apply {
            val data = getSerializable("data")
            if (data == null) {
                houseStruct = HouseStruct(5, 3, 3)
            }
            initAdapter()
        }
    }

    private fun initAdapter() {
        rv_room.adapter = getLayoutAdapter(HouseStructValue.ROOM)
        rv_hall.adapter = getLayoutAdapter(HouseStructValue.HALL)
        rv_toilet.adapter = getLayoutAdapter(HouseStructValue.TOILET)
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(mContext, 5)
    }

    private fun getLayoutAdapter(type: Int): HouseStructAdapter {
        when (type) {
            HouseStructValue.ROOM -> {
                val roomData = getDataByType(type, maxRoomItem)
                return HouseStructAdapter(roomData, rv_room, type)
            }

            HouseStructValue.HALL -> {
                val roomData = getDataByType(type, maxHallItem)
                return HouseStructAdapter(roomData, rv_hall, type)
            }

            HouseStructValue.TOILET -> {
                val roomData = getDataByType(type, maxToiletItem)
                return HouseStructAdapter(roomData, rv_toilet, type)
            }
        }

        throw RuntimeException("no this type")
    }

    private fun getDataByType(type: Int, maxItemCount: Int): MutableList<HouseStructValue> {
        val roomData = mutableListOf<HouseStructValue>()

        var start = 1

        for (i in start..maxItemCount) {
            val houseStructValue: HouseStructValue
            if (i == maxItemCount) {
                houseStructValue = HouseStructValue(null, true)
            } else {
                houseStructValue = HouseStructValue(i)
            }
            roomData.add(houseStructValue)
        }
        return roomData
    }

    private fun getAdapterData(type: Int): List<HouseStruct> {

        when (type) {
            HouseStructValue.ROOM -> {

            }
            HouseStructValue.HALL -> {

            }
            HouseStructValue.TOILET -> {

            }
        }

        throw RuntimeException("no this type")
    }


}
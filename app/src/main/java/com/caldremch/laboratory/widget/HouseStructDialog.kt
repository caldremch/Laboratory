package com.caldremch.laboratory.widget

import android.view.Gravity
import android.view.View
import android.widget.Toast
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
 * 厅 max: 4
 * 卫 max: 4
 *
 **/

class HouseStructDialog(parent: Any, var strict: Boolean = false) : BaseDialog(parent) {

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

    var listener: SimpleListener<HouseStruct>? = null

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
            if (strict) {

                if (!roomAdapter.hasSelected() || !hallAdapter.hasSelected() || !toiletAdapter.hasSelected()) {
                    Toast.makeText(mContext, "please complete input", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val roomValue = roomAdapter.getSelectData()!!
                val hallValue = hallAdapter.getSelectData()!!
                val toiletValue = toiletAdapter.getSelectData()!!

                if (roomValue.isEditText && roomValue.value == null) {
                    Toast.makeText(mContext, "please complete input", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (hallValue.isEditText && hallValue.value == null) {
                    Toast.makeText(mContext, "please complete input", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (toiletValue.isEditText && toiletValue.value == null) {
                    Toast.makeText(mContext, "please complete input", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

            }



            listener?.onData(
                HouseStruct(
                    roomAdapter.getSelectData()?.value,
                    hallAdapter.getSelectData()?.value,
                    toiletAdapter.getSelectData()?.value
                )
            )
            dismiss()

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
            if (it >= maxRoomItem || it == 0) {
                roomAdapter.mData[maxRoomItem - 1].value = it
                roomAdapter.mData[maxRoomItem - 1].isSelect = true
                roomAdapter.selectedPos = maxRoomItem - 1
            } else {
                roomAdapter.mData.forEachIndexed { index, houseStructValue ->
                    if (houseStructValue.value == it) {
                        roomAdapter.mData[index].isSelect = true
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
            if (it > 2) {
                adapter.mData[maxItemCount - 1].value = it
                adapter.mData[maxItemCount - 1].isSelect = true
                adapter.selectedPos = maxItemCount - 1
            } else {
                adapter.mData.forEachIndexed { index, houseStructValue ->
                    if (houseStructValue.value == it) {
                        adapter.mData[index].isSelect = true
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
            val houseStructValue: HouseStructValue = if (i == maxItemCount - 1) {
                HouseStructValue(null, true)
            } else {
                HouseStructValue(if (type == HouseStructValue.ROOM) i + 1 else i)
            }
            roomData.add(houseStructValue)
        }

        when (type) {
            HouseStructValue.ROOM -> roomAdapter = HouseStructAdapter(roomData, rv_room, type)
            HouseStructValue.HALL -> hallAdapter = HouseStructAdapter(roomData, rv_hall, type)
            HouseStructValue.TOILET -> toiletAdapter = HouseStructAdapter(roomData, rv_toilet, type)
        }
    }


}
package com.caldremch.pickerview.adapter

import androidx.recyclerview.widget.RecyclerView
import com.caldremch.pickerview.WheelRecyclerView
import com.caldremch.pickerview.WheelView

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-28 14:48
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class StringSelectAdapter<T : RecyclerView.ViewHolder>(
     adapter: WheelAdapter<T>,
     itemSize: Int, //item 高度
     myItemCount: Int, //上或下的 item 数量
     itemWidth: Float
) : SimpleWheelAdapter<T>(
    adapter = adapter,
    orientation = WheelRecyclerView.GRAVITY_CENTER,
    itemSize = itemSize,
    myItemCount = myItemCount,
    itemWidth = itemWidth
) {





}
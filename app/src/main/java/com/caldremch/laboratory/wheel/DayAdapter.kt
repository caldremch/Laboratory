package com.caldremch.laboratory.wheel

import android.util.Log
import android.view.LayoutInflater
import com.caldremch.laboratory.R
import com.caldremch.laboratory.date.DateViewHolder
import com.caldremch.pickerview.adapter.WheelAdapter

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-28 01:42
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/


class DayAdapter : WheelAdapter<DateViewHolder>() {

    override fun getItemCount(): Int {
        return 20
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, viewType: Int): DateViewHolder {
        return DateViewHolder(
            inflater.inflate(
                R.layout.picker_view_day_item,
                null,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        Log.d("tag", "111")
    }


}
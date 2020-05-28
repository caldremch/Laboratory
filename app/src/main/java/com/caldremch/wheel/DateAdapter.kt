package com.caldremch.wheel

import android.util.Log
import android.view.LayoutInflater
import com.caldremch.date.DateViewHolder
import com.caldremch.laboratory.R
import com.caldremch.pickerview.adapter.WheelAdapter

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-28 01:42
 *
 * @email caldremch@163.com
 *
 * @describe 日期列表 Adapter
 *
 **/
class DateAdapter(val type: Int) : WheelAdapter<DateViewHolder>() {

    companion object {
        val YEAR = 1
        val MONTH = 2
        val DAY = 3
    }

    val data = mutableListOf<String>()

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, viewType: Int): DateViewHolder {
        var layoutResId = R.layout.picker_view_year_item
        when (type) {
            YEAR -> layoutResId = R.layout.picker_view_year_item
            MONTH -> layoutResId = R.layout.picker_view_month_item
            DAY -> layoutResId = R.layout.picker_view_day_item
        }
        return DateViewHolder(inflater.inflate(layoutResId, null, false))
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.textView.text = data[position]
    }


}
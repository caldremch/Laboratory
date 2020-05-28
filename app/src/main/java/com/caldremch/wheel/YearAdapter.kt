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
 * @describe
 *
 **/


class YearAdapter : WheelAdapter<DateViewHolder>() {

    val data = mutableListOf<String>()

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, viewType: Int): DateViewHolder {
        return DateViewHolder(
            inflater.inflate(R.layout.picker_view_year_item, null, false)
        )
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.textView.text = data[position]
    }


}
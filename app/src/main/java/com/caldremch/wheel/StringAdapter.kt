package com.caldremch.wheel

import android.util.Log
import android.view.LayoutInflater
import com.caldremch.date.DateViewHolder
import com.caldremch.laboratory.R
import com.caldremch.pickerview.adapter.WheelAdapter

class StringAdapter : WheelAdapter<DateViewHolder>() {

    val data = mutableListOf<String>()

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, viewType: Int): DateViewHolder {
        return DateViewHolder(
            inflater.inflate(
                R.layout.picker_string_item,
                null,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        Log.d("tag", "111")
    }


}
package com.caldremch.wheel

import android.util.Log
import android.view.LayoutInflater
import com.caldremch.date.DateViewHolder
import com.caldremch.laboratory.R
import com.caldremch.pickerview.adapter.WheelAdapter

class TestAdapter : WheelAdapter<DateViewHolder>() {

    override fun getItemCount(): Int {
        return 20
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, viewType: Int): DateViewHolder {
        return DateViewHolder(
            inflater.inflate(
                R.layout.test_item,
                null,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        Log.d("tag", "111")
    }


}
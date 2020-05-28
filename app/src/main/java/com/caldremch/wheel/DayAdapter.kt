package com.caldremch.wheel

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
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


class DayAdapter : WheelAdapter<TestViewHolder>() {

    override fun getItemCount(): Int {
        return 20
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, viewType: Int): TestViewHolder {
        return TestViewHolder(inflater.inflate(R.layout.picker_view_day_item ,null, false))
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        Log.d("tag", "111")
    }


}
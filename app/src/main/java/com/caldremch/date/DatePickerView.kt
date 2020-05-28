package com.caldremch.date

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.caldremch.laboratory.R
import com.caldremch.wheel.DayAdapter
import com.caldremch.wheel.MonthAdapter
import com.caldremch.wheel.TestAdapter
import com.caldremch.wheel.YearAdapter
import kotlinx.android.synthetic.main.date_picker_view.view.*

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-28 16:11
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class DatePickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    init {
        View.inflate(context, R.layout.date_picker_view, this)
        initData()
    }

    private fun initData() {
    }

    fun refresh() {
        wv_year.post { wv_year.setAdapter(YearAdapter()) }
        wv_month.post { wv_month.setAdapter(MonthAdapter()) }
        wv_day.post { wv_day.setAdapter(DayAdapter()) }
    }
}
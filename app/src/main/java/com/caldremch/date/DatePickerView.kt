package com.caldremch.date

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.caldremch.laboratory.R
import com.caldremch.pickerview.callback.OnItemSelectedListener
import com.caldremch.wheel.YearAdapter
import kotlinx.android.synthetic.main.date_picker_view.view.*
import java.util.*

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-28 16:11
 *
 * @email caldremch@163.com
 *
 * @describe 是否能显示超过当前月份?
 *
 **/
class DatePickerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var maxDaysMap: MutableMap<Int, MutableList<String>>
    val yearAdapter = YearAdapter()
    val monthAdapter = YearAdapter()
    val dayAdapter = YearAdapter()

    private var cYear = 0
    private var cMonth = 0
    private var cDay = 0

    private var currentYearIndex = 0
    private var currentMonthIndex = 0
    private var currentDayIndex = 0

    var listener: OnDateSelectedListener? = null

    init {
        View.inflate(context, R.layout.date_picker_view, this)
        initData()
        initEvent()
    }

    private fun initData() {
        //获取当前时间
        initCurrentDate()
        //获取最大天数的Map
        maxDaysMap = DateInfoUtils.initMaxDaysByMap()

        //年份数据
        yearAdapter.data.clear()
        yearAdapter.data.addAll(DateInfoUtils.getYears(2010, 2020))

        //月份数据
        monthAdapter.data.addAll(DateInfoUtils.getMonth())

        //日期数据
        //获取当前月的天数
        val days = getMaxDayByYearAndMonth(cYear, cMonth)
        dayAdapter.data.addAll(maxDaysMap[days]!!)

        //设置年份 adapter, 并滑到当前年分
        wv_year.post {
            wv_year.setAdapter(yearAdapter)
            val indexOfCurrentYear = yearAdapter.data.indexOf("${cYear}年")
            if (indexOfCurrentYear != -1) {
                //滑动到当前日期
                wv_year.setCurrentPos(indexOfCurrentYear)
            }
        }

        //设置月份 adapter, 并滑到当前月份
        wv_month.post {
            wv_month.setAdapter(monthAdapter)
            val indexOfCurrentMonth = monthAdapter.data.indexOf("${cMonth}月")
            if (indexOfCurrentMonth != -1) {
                //滑动到当前月份
                wv_month.setCurrentPos(indexOfCurrentMonth)
            }

        }

        //设置天数 adapter, 当前日期
        wv_day.post {
            wv_day.setAdapter(dayAdapter)
            val indexOfCurrentDay = dayAdapter.data.indexOf("${cDay}日")
            if (indexOfCurrentDay != -1) {
                //初始化当前的日期
                wv_day.setCurrentPos(indexOfCurrentDay)
            }
        }


    }

    private fun initEvent() {


        wv_year.listener = object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                currentYearIndex = index
                //设置天数
                dayAdapter.data.clear()
                dayAdapter.data.addAll(getDaysOfYearAndMonth())
                dayAdapter.notifyDataSetChanged()
                callback()
            }
        }


        wv_month.listener = object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {

                //根据当前年份和月份 获取最大天数
                currentMonthIndex = index
                dayAdapter.data.clear()
                dayAdapter.data.addAll(getDaysOfYearAndMonth())
                dayAdapter.notifyDataSetChanged()
                callback()

            }
        }

        wv_day.listener = object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                //根据当前年份和月份 获取最大天数
                currentDayIndex = index
                callback()
            }
        }
    }

    fun callback() {
        //数据回调
        val dateStr = yearAdapter.data[currentYearIndex] +
                monthAdapter.data[currentMonthIndex] +
                dayAdapter.data[currentDayIndex]
        listener?.onItemSelected(dateStr)
    }

    //根据 index 获取年份
    fun getYearOfIndex(): Int {
        return yearAdapter.data[currentYearIndex].replace("年", "").toInt()
    }

    //根据 index 获取月份
    fun getMonthOfIndex(): Int {
        return monthAdapter.data[currentMonthIndex].replace("月", "").toInt()
    }

    //根据年月获取最大天数集合
    fun getDaysOfYearAndMonth(): MutableList<String> {
        val maxDay = getMaxDayByYearAndMonth(getYearOfIndex(), getMonthOfIndex())
        return maxDaysMap[maxDay]!!
    }

    /**
     * 获取year 年, month月份的最大天数
     */
    private fun getMaxDayByYearAndMonth(year: Int, month: Int): Int {
        val instance = Calendar.getInstance(Locale.CHINA)
        instance[Calendar.YEAR] = year
        instance[Calendar.MONTH] = month - 1
        return instance.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    /**
     * 获取当前日期信息
     */
    private fun initCurrentDate() {
        val instance = Calendar.getInstance(Locale.CHINA)
        cYear = instance[Calendar.YEAR]
        cMonth = instance[Calendar.MONTH] + 1
        cDay = instance[Calendar.DAY_OF_MONTH]
    }

    fun refresh() {
//        wv_month.post { wv_month.setAdapter(MonthAdapter()) }
//        wv_day.post { wv_day.setAdapter(DayAdapter()) }
    }


}
package com.caldremch.date

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.caldremch.laboratory.R
import com.caldremch.pickerview.callback.OnItemSelectedListener
import com.caldremch.wheel.DateAdapter
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
    val yearAdapter = DateAdapter(DateAdapter.YEAR)
    val monthAdapter = DateAdapter(DateAdapter.MONTH)
    val dayAdapter = DateAdapter(DateAdapter.DAY)

    private var cYear = 0
    private var cMonth = 0
    private var cDay = 0

    private var currentYearIndex = -1
    private var currentMonthIndex = -1
    private var currentDayIndex = -1

    val calender by lazy { Calendar.getInstance(Locale.CHINA) }


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
        yearAdapter.data.addAll(DateInfoUtils.getYears(2010, 2030))

        //月份数据
        monthAdapter.data.addAll(DateInfoUtils.getMonth())

        //日期数据
        //获取当前月的天数
        val days = getMaxDayByYearAndMonth(cYear, cMonth)
        dayAdapter.data.addAll(maxDaysMap[days]!!)

        //设置年份 adapter, 并滑到当前年分
        wv_year.post {
            wv_year.setAdapter(yearAdapter)
            //dialog未显示的时候, 先设置了日期时间,这时候第一次开启弹窗的时候, 不需要初始化当前日期(仿干扰)
            if (currentYearIndex == -1){
                setCurrentYear(cYear)
            }
        }

        //设置月份 adapter, 并滑到当前月份
        wv_month.post {
            wv_month.setAdapter(monthAdapter)
            //dialog未显示的时候, 先设置了日期时间,这时候第一次开启弹窗的时候, 不需要初始化当前日期(仿干扰)
            if (currentMonthIndex == -1){
                setCurrentMonth(cMonth)
            }
        }

        //设置天数 adapter, 当前日期
        wv_day.post {
            wv_day.setAdapter(dayAdapter)
            //dialog未显示的时候, 先设置了日期时间,这时候第一次开启弹窗的时候, 不需要初始化当前日期(仿干扰)
            if (currentDayIndex == -1){
                setCurrentDay(cDay)
            }
        }


    }

    private fun setCurrentDay(day: Int) {
        currentDayIndex = dayAdapter.data.indexOf("${day}日")
        if (currentDayIndex != -1) {
            //初始化当前的日期
            wv_day.post {
                wv_day.setCurrentPos(currentDayIndex)
            }
        }
    }

    private fun setCurrentMonth(month: Int) {
         currentMonthIndex = monthAdapter.data.indexOf("${month}月")
        if (currentMonthIndex != -1) {
            //滑动到当前月份
            wv_month.post {
                wv_month.setCurrentPos(currentMonthIndex)
            }
        }
    }

    private fun setCurrentYear(year: Int) {
         currentYearIndex = yearAdapter.data.indexOf("${year}年")
        if (currentYearIndex != -1) {
            //滑动到当前日期
            wv_year.post {
                wv_year.setCurrentPos(currentYearIndex)
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

        if (currentYearIndex>=yearAdapter.data.size){
            currentYearIndex = yearAdapter.data.size-1
        }

        if (currentMonthIndex>=monthAdapter.data.size){
            currentMonthIndex = monthAdapter.data.size-1
        }

        if (currentDayIndex>=dayAdapter.data.size){
            currentDayIndex = dayAdapter.data.size-1
        }

        //数据回调
        listener?.onItemSelected(
            yearAdapter.data[currentYearIndex].replace("年", ""),
            monthAdapter.data[currentMonthIndex].replace("月", "") ,
            dayAdapter.data[currentDayIndex].replace("日", "")
        )

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
        cYear = calender[Calendar.YEAR]
        cMonth = calender[Calendar.MONTH] + 1
        cDay = calender[Calendar.DAY_OF_MONTH]
    }


    //设置为当前系统时间
    fun today() {
        setDate(cYear, cMonth, cDay)
    }

    //设置时间, 要注意的是, 当设置了月份的时候, 这里的最大日是会有变动的
    fun setDate(year:Int, month: Int, day: Int){
        setCurrentYear(year)
        setCurrentMonth(month)
        //改变最大日
        val days = getMaxDayByYearAndMonth(year, month)
        dayAdapter.data.clear()
        dayAdapter.data.addAll(maxDaysMap[days]!!)
        setCurrentDay(day)
    }

}
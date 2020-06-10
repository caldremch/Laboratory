package com.caldremch.date

import android.content.Context
import android.util.AttributeSet
import android.util.Log
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
 * @describe 添加限制, 是否限制到今天的日期
 *
 **/

class DateInfo(var year: Int = 0, var month: Int = 0, var day: Int = 0)

class DatePickerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var maxDaysMap: MutableMap<Int, MutableList<Int>>
    val yearAdapter = DateAdapter(DateAdapter.YEAR)
    val monthAdapter = DateAdapter(DateAdapter.MONTH)
    val dayAdapter = DateAdapter(DateAdapter.DAY)

    private var cYear = 0
    private var cMonth = 0
    private var cDay = 0

    private var currentYearIndex = -1
    private var currentMonthIndex = -1
    private var currentDayIndex = -1

    private val INIT_START_YEAR = 2010

    private val selectedDate = DateInfo()
    private val selectedDateIndex = DateInfo()

    val calender by lazy { Calendar.getInstance(Locale.CHINA) }

    private val fullMonthList by lazy { DateInfoUtils.getMonth(12) }
    private lateinit var currentMonthList: List<Int>

    /**
     * 是否限制到今天的日期(滚轮无法滑动超过这个时间)
     */
    var limitUtilToday = false

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
        maxDaysMap = DateInfoUtils.initMaxDaysByMap(cDay)

        //年份数据
        val startYear = INIT_START_YEAR

        //只能是INIT_START_YEAR 以后的年份
        if (cYear < startYear) {
            cYear = startYear
        }

        yearAdapter.data.clear()
        yearAdapter.data.addAll(DateInfoUtils.getYears(startYear, cYear))

        //月份数据
        currentMonthList = DateInfoUtils.getMonth(if (limitUtilToday) cMonth else 12)
        monthAdapter.data.addAll(currentMonthList)

        //日期数据
        //获取当前月的天数
        dayAdapter.data.addAll(
            if (limitUtilToday) maxDaysMap[getMaxDayByYearAndMonth(
                cYear,
                cMonth
            )]!! else maxDaysMap[cDay]!!
        )

        //设置年份 adapter, 并滑到当前年分
        wv_year.post {
            wv_year.setAdapter(yearAdapter)
            //dialog未显示的时候, 先设置了日期时间,这时候第一次开启弹窗的时候, 不需要初始化当前日期(仿干扰)
            if (currentYearIndex == -1) {
                setCurrentYear(cYear)
                //设置当前年的月份数组
            }

        }

        //设置月份 adapter, 并滑到当前月份, 如果是当前年, 那么
        wv_month.post {
            wv_month.setAdapter(monthAdapter)
            //dialog未显示的时候, 先设置了日期时间,这时候第一次开启弹窗的时候, 不需要初始化当前日期(仿干扰)
            if (currentMonthIndex == -1) {
                setCurrentMonth(cMonth)
            }
        }

        //设置天数 adapter, 当前日期
        wv_day.post {
            wv_day.setAdapter(dayAdapter)
            //dialog未显示的时候, 先设置了日期时间,这时候第一次开启弹窗的时候, 不需要初始化当前日期(仿干扰)
            if (currentDayIndex == -1) {
                setCurrentDay(cDay)
            }
        }

        selectedDate.year = cYear
        selectedDate.month = cMonth
        selectedDate.day = cDay

    }

    private fun setCurrentDay(day: Int) {
        currentDayIndex = dayAdapter.data.indexOf(day)
        if (currentDayIndex != -1) {
            selectedDateIndex.day = currentDayIndex
            //初始化当前的日期
            wv_day.post {
                wv_day.setCurrentPos(currentDayIndex)
            }
        }
    }

    private fun setCurrentMonth(month: Int) {
        currentMonthIndex = monthAdapter.data.indexOf(month)
        if (currentMonthIndex != -1) {
            selectedDateIndex.month = currentMonthIndex

            //滑动到当前月份
            wv_month.post {
                wv_month.setCurrentPos(currentMonthIndex)
            }
        }
    }

    private fun setCurrentYear(year: Int) {
        currentYearIndex = yearAdapter.data.indexOf(year)
        if (currentYearIndex != -1) {
            selectedDateIndex.year = currentYearIndex
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
                selectedDateIndex.year = index
                selectedDate.year = yearAdapter.data[index]

                //设置天数
                setDaysShowByYM()

                //设置月份
                setMonthByYearSelect()

                callback()
            }
        }


        wv_month.listener = object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {

                //根据当前年份和月份 获取最大天数
                currentMonthIndex = index
                selectedDateIndex.month = index
                selectedDate.month = monthAdapter.data[index]
                setDaysShowByYM()
                callback()

            }
        }

        wv_day.listener = object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                //根据当前年份和月份 获取最大天数
                currentDayIndex = index
                selectedDateIndex.day = index
                selectedDate.day = dayAdapter.data[index]
                callback()
            }
        }
    }

    //年滚动的时候
    private fun setMonthByYearSelect() {

        if (limitUtilToday && cYear == selectedDate.year) {
            //如果是当前年,需求改变月数组
            monthAdapter.data.clear()
            monthAdapter.data.addAll(currentMonthList)
            monthAdapter.notifyDataSetChanged()
        } else if (monthAdapter.data.size != 12) {
            //从当前年滑动到其他年
            monthAdapter.data.clear()
            monthAdapter.data.addAll(fullMonthList)
            monthAdapter.notifyDataSetChanged()
        }
    }

    //当设定了年月时, 设置天数列表
    private fun setDaysShowByYM() {
        if (limitUtilToday && selectedDate.year == cYear && selectedDate.month == cMonth) {
            dayAdapter.data.clear()
            dayAdapter.data.addAll(maxDaysMap[cDay]!!)
            dayAdapter.notifyDataSetChanged()
        } else {
            dayAdapter.data.clear()
            val maxDay = getMaxDayByYearAndMonth(selectedDate.year, selectedDate.month)
            dayAdapter.data.addAll(maxDaysMap[maxDay]!!)
            dayAdapter.notifyDataSetChanged()
        }
    }

    fun callback() {

        if (currentYearIndex >= yearAdapter.data.size) {
            currentYearIndex = yearAdapter.data.size - 1
        }

        if (currentMonthIndex >= monthAdapter.data.size) {
            currentMonthIndex = monthAdapter.data.size - 1
        }

        if (currentDayIndex >= dayAdapter.data.size) {
            currentDayIndex = dayAdapter.data.size - 1
        }

        //数据回调
        listener?.onItemSelected(
            yearAdapter.data[currentYearIndex],
            monthAdapter.data[currentMonthIndex],
            dayAdapter.data[currentDayIndex]
        )

    }

    //根据 index 获取年份
    fun getYearOfIndex(): Int {
        return yearAdapter.data[currentYearIndex]
    }

    //根据 index 获取月份
    fun getMonthOfIndex(): Int {
        return monthAdapter.data[currentMonthIndex]
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
    /**
     * 如果开启了限制, 这里设置的日期不能大于当前日期, 设置了无效
     */
    fun setDate(year: Int, month: Int, day: Int) {

        val testStart = System.currentTimeMillis()
        if (limitUtilToday) {
            //要设定的时间
            val settingDate = Calendar.getInstance()
            settingDate.set(Calendar.YEAR, year)
            settingDate.set(Calendar.MONTH, month - 1)
            settingDate.set(Calendar.DAY_OF_MONTH, day)

            //当前时间
            val currentDate = Calendar.getInstance()
            //消除时分秒进行比较
            currentDate.clear(Calendar.HOUR_OF_DAY)
            currentDate.clear(Calendar.MINUTE)
            currentDate.clear(Calendar.SECOND)

            if (settingDate.after(currentDate)) {
                return
            }

            //设置年
            setCurrentYear(year)

            //设置最大可选月
            if (year == cYear) {
                monthAdapter.data.clear()
                monthAdapter.data.addAll(currentMonthList)
                monthAdapter.notifyDataSetChanged()
            }

            //选中当前月
            setCurrentMonth(month)

            //设置当前的日期列表
            if (year == cYear && month == cMonth) {
                dayAdapter.data.clear()
                dayAdapter.data.addAll(maxDaysMap[cDay]!!)
            } else {
                dayAdapter.data.clear()
                dayAdapter.data.addAll(maxDaysMap[getMaxDayByYearAndMonth(year, month)]!!)
            }

            //选中当前的日期
            setCurrentDay(day)

        } else {
            setCurrentYear(year)
            setCurrentMonth(month)
            val days = getMaxDayByYearAndMonth(year, month)
            dayAdapter.data.clear()
            dayAdapter.data.addAll(maxDaysMap[days]!!)
            dayAdapter.notifyDataSetChanged()
            setCurrentDay(day)
        }

        val testEnd = System.currentTimeMillis()

        Log.d("time", "间隔时间:${testEnd - testStart}")


    }
}
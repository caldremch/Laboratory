package com.caldremch.date

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.caldremch.dialog.BottomDialog
import com.caldremch.laboratory.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

/**
 *
 * @author Caldremch
 *
 * @date 2020-06-01
 *
 * @email caldremch@163.com
 *
 * @describe 时间选择弹窗
 *
 * limit: 三个月的限制
 *
 **/
class DatePickDialog(val myContext: Context, var limit: Boolean = false) : BottomDialog(myContext) {


    companion object {
        val TODAY = 1 //今天
        val YESTERDAY = 2 //昨天
        val THIS_WEEK = 3 //本周
        val LAST_WEEK = 4 //上周
        val THIS_MONTH = 5 //本月
        val LAST_MONTH = 6 //上月

    }

    private lateinit var pickerView: DatePickerView
    private lateinit var tvStartDate: TextView
    private lateinit var tvEndDate: TextView

    private var startDateStr: String? = null //当前选中开始日期字符串
    private var endDateStr: String? = null //当前选中结束日期字符串

    //分隔符
    private val splitCharater = "/"

    //最大跨度月份, 开始时间与结束时间的月差
    private val maxMonth = 3

    //时间跨度判断
    //上次开始/结束选中的时间, 用于状态恢复
    private val startTime by lazy { SimpleDateInfo(0, 0, 0) }
    private val endTime by lazy { SimpleDateInfo(0, 0, 0) }

    //提示语
    private val tips = "日期选择时间跨度不能超过三个月"

    //显示格式
    private val dateFormat = SimpleDateFormat(
        "yyyy${splitCharater}M${splitCharater}d",
        Locale.CHINA
    )

    //回调
    var listener: OnDateSelectedListener? = null


    /**
     * 超过 3 个月 || 结束时间大于其实时间
     */
    private fun isCheckOk(): Boolean {

        if (TextUtils.isEmpty(startDateStr) || TextUtils.isEmpty(endDateStr)) {
            return false
        }

        val result =
//
//        if (result == DateInfoUtils.ERROR_OVER) {
//            Toast.makeText(myContext, "起始时间必须在终止时间之前", Toast.LENGTH_SHORT).show()
//            return false
//        } else if (limit && (result == DateInfoUtils.ERROR_OVER_THREE_MONTH || result > 3)) {
//            Toast.makeText(myContext, "查询时间的跨度不能超过3个月", Toast.LENGTH_SHORT).show()
//            return false
//        }

        return DateInfoUtils.checkMonth(dateFormat, limit,  startDateStr!!, endDateStr!!)
    }

    //业务需求, 当结束时间-开始时间>3 个月的时候, 开始时间不能继续向前滑动
    //当滑动超过的时候, 滚回
    private fun checkThreeLimit(
        b: Boolean,  //是否是开始时间
        year: Int,
        month: Int,
        day: Int
    ) {
        if (b) {
            //年份变动则回滚
            if (year != startTime.year || abs(month - endTime.month) > 3) {
                rollbackToDate(startTime)
                checkTips()
            } else {
                setLastDateInfo(startTime, year, month, day)
            }
        } else {
            if (year != endTime.year || abs(month - startTime.month) > 3) {
                rollbackToDate(endTime)
                checkTips()
            } else {
                setLastDateInfo(endTime, year, month, day)
            }
        }
    }

    //存为上一次选中的时间
    private fun setLastDateInfo(simpleDateInfo: SimpleDateInfo, year: Int, month: Int, day: Int) {
        simpleDateInfo.year = year
        simpleDateInfo.month = month
        simpleDateInfo.day = day
    }

    //回滚到上一次选中的时间
    private fun rollbackToDate(simpleDateInfo: SimpleDateInfo) {
        pickerView.setDate(simpleDateInfo.year, simpleDateInfo.month, simpleDateInfo.day)
    }

    private fun checkTips() {
        Toast.makeText(myContext, tips, Toast.LENGTH_SHORT).show()
    }

    private fun onCallback() {
        try {
            val startDate = dateFormat.parse(startDateStr!!)
            val endDate = dateFormat.parse(endDateStr!!)

            if (startDate != null && endDate != null) {
                listener?.onDateSelected(getDate(true, startDate), getDate(false, endDate))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //根据时间字符串获取对应时间戳, 包含时分秒
    private fun getDate(isStart: Boolean, date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        if (isStart) {
            DateInfoUtils.dayStart(calendar)
        } else {
            DateInfoUtils.dayEnd(calendar)
        }
        return calendar.time
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_date_picker
    }

    override fun iniTest() {
        setCanceledOnTouchOutside(true)

        pickerView = myContentView.findViewById(R.id.dpv)

        //截止今天
        pickerView.limitUtilToday = true

        tvStartDate = myContentView.findViewById<TextView>(R.id.tv_start_date)
        tvEndDate = myContentView.findViewById<TextView>(R.id.tv_end_date)

        tvStartDate.isSelected = false
        tvEndDate.isSelected = false

        tvStartDate.text = "开始时间"
        tvEndDate.text = "结束时间"

        tvStartDate.setOnClickListener {
            setSelectStyle(tvStartDate, true)
            setSelectStyle(tvEndDate, false)

            if (TextUtils.isEmpty(startDateStr)) {
                return@setOnClickListener
            }
            setDateInfo(startDateStr!!)
        }

        tvEndDate.setOnClickListener {
            setSelectStyle(tvStartDate, false)
            setSelectStyle(tvEndDate, true)
            if (TextUtils.isEmpty(endDateStr)) {
                return@setOnClickListener
            }
            setDateInfo(endDateStr!!)
        }

        myContentView.findViewById<View>(R.id.tv_cancel).setOnClickListener {
            dismiss()
        }

        myContentView.findViewById<View>(R.id.tv_confirm).setOnClickListener {
            //回调给前端 时间戳回调
            if (TextUtils.isEmpty(startDateStr)) {
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(endDateStr)) {
                return@setOnClickListener
            }

            //检查限制范围
            if (!isCheckOk()) {
                return@setOnClickListener
            }

            //回调事件
            onCallback()
            dismiss()
        }

        //时间选择回调
        pickerView.listener = object : OnDateSelectedListener {

            override fun onItemSelected(year: Int, month: Int, day: Int) {


                if (pickerView.todayForbidden){
                    //转换当前选中的时间为日期对象
                    val selectedDateCalendar = DateInfoUtils.transfer(year, month, day)
                    //清除时分秒毫秒
                    DateInfoUtils.clearDateHms(selectedDateCalendar)
                    //和今天日期对比
                    val compareResult = selectedDateCalendar.compareTo(pickerView.todayCalendarNoHms)

                    Log.d("maxOver","selectedDateCalendar: ${dateFormat.format(selectedDateCalendar.time)}")
                    Log.d("maxOver","todayCalendarNoHms: ${dateFormat.format(pickerView.todayCalendarNoHms.time)}")
                    Log.d("maxOver","compareResult: ${compareResult}")


                    if (compareResult > 0 || compareResult == 0) {
                        //rollback to yesterday
                        val yesterDayCalendar = DateInfoUtils.getYesterDayCalender()
                        val resetToYear = yesterDayCalendar[Calendar.YEAR]
                        val resetToMonth = yesterDayCalendar[Calendar.MONTH]+1
                        val resetToDay = yesterDayCalendar[Calendar.DAY_OF_MONTH]

                        //超过后, 设置为昨天
                        pickerView.setDate(resetToYear, resetToMonth, resetToDay)
                        return
                    }
                }

                //时间字符串回调
                val str = "$year$splitCharater$month$splitCharater$day"
                if (tvStartDate.isSelected) {
                    tvStartDate.text = str
                    startDateStr = str
                } else {
                    tvEndDate.text = str
                    endDateStr = str
                }
            }
        }
    }

    private fun setSelectStyle(textView: TextView, isSelect: Boolean) {
        textView.isSelected = isSelect
        textView.setTextColor(Color.parseColor(if (isSelect) "#257BF4" else "#9699A0"))
    }

    //设置日期开始时间
    fun setDate(type: Int) {
        when (type) {
            TODAY -> {
                val todayStr = DateInfoUtils.getToday(dateFormat)
                setTextViewDate(todayStr, todayStr)

                //考核分的操作
                if (true){
                    val yesterDayStr = DateInfoUtils.getYesterday(dateFormat)
                    //选择今天的时候, 滚轮滚到昨天, 但是现实是今天
                    setDateInfo(yesterDayStr)
                }
//                pickerView.today()
            }
            YESTERDAY -> {
                val yesterDayStr = DateInfoUtils.getYesterday(dateFormat)
                setTextViewDate(yesterDayStr, yesterDayStr)
                setDateInfo(DateInfoUtils.getYesterday(dateFormat))
            }
            THIS_WEEK -> {
                setTextViewDate(
                    DateInfoUtils.thisWeekMonday(dateFormat),
                    DateInfoUtils.thisWeekSunday(dateFormat)
                )
                setDateInfo(DateInfoUtils.thisWeekMonday(dateFormat))
            }
            LAST_WEEK -> {
                setTextViewDate(
                    DateInfoUtils.lastWeekMonday(dateFormat),
                    DateInfoUtils.lastWeekSunday(dateFormat)
                )
                setDateInfo(DateInfoUtils.lastWeekMonday(dateFormat))
            }
            THIS_MONTH -> {
                setTextViewDate(
                    DateInfoUtils.getFirstDayOfCurrentMonth(dateFormat),
                    DateInfoUtils.getLastDayOfCurrentMonth(dateFormat)
                )
                setDateInfo(DateInfoUtils.getFirstDayOfCurrentMonth(dateFormat))
            }
            LAST_MONTH -> {
                setTextViewDate(
                    DateInfoUtils.getFirstDayOfLastMonth(dateFormat),
                    DateInfoUtils.getLastDayOfLastMonth(dateFormat)
                )
                setDateInfo(DateInfoUtils.getFirstDayOfLastMonth(dateFormat))
            }
        }
    }

    private fun setTextViewDate(start: String, end: String) {

        //设置 textView
        tvStartDate.text = start
        tvEndDate.text = end

        //设置字符串
        startDateStr = start
        endDateStr = end

        //默认高亮第一个
        setSelectStyle(tvStartDate, true)
        setSelectStyle(tvEndDate, false)

        //保存当前选择的年月日
        saveDateInfo(start, end)
    }

    //保存当前日期年月日
    private fun saveDateInfo(start: String?, end: String?) {

        if (!TextUtils.isEmpty(start)) {
            val startDateInfo = start!!.split(splitCharater)
            if (startDateInfo.size == 3) {
                setLastDateInfo(
                    startTime,
                    startDateInfo[0].toInt(),
                    startDateInfo[1].toInt(),
                    startDateInfo[2].toInt()
                )
            }
        }

        if (!TextUtils.isEmpty(end)) {
            val endDateInfo = end!!.split(splitCharater)
            if (endDateInfo.size == 3) {
                setLastDateInfo(
                    endTime,
                    endDateInfo[0].toInt(),
                    endDateInfo[1].toInt(),
                    endDateInfo[2].toInt()
                )
            }
        }
    }

    //设置日期选中
    private fun setDateInfo(dateStr: String) {
        val dateInfo = dateStr.split(splitCharater)
        if (dateInfo.size == 3) {
            pickerView.post {
                pickerView.setDate(dateInfo[0].toInt(), dateInfo[1].toInt(), dateInfo[2].toInt())
            }
        }
    }


}
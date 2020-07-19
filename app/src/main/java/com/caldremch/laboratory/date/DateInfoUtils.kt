package com.caldremch.laboratory.date

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-28 18:02
 *
 * @email caldremch@163.com
 *
 * @describe  年月日处理工具
 *
 **/
object DateInfoUtils {

    const val YEAR_SUFFIX = "年"
    const val MONTH_SUFFIX = "月"
    const val DAY_SUFFIX = "日"


    //初始化好maxDay, 最大天数

    fun initMaxDaysByMap(cDay: Int): MutableMap<Int, MutableList<Int>> {
        val maxDayMap = mutableMapOf<Int, MutableList<Int>>()

        val days28 = mutableListOf<Int>()
        val days29 = mutableListOf<Int>()
        val days30 = mutableListOf<Int>()
        val days31 = mutableListOf<Int>()
        val cdays = mutableListOf<Int>() //当前日

        for (i in 1..32) {

            if (i <= cDay) {
                cdays.add(i)
            }

            if (i <= 28) {
                days28.add(i)
            }
            if (i <= 29) {
                days29.add(i)
            }
            if (i <= 30) {
                days30.add(i)
            }
            if (i <= 31) {
                days31.add(i)
            }
        }

        maxDayMap[28] = days28
        maxDayMap[29] = days29
        maxDayMap[30] = days30
        maxDayMap[31] = days31
        maxDayMap[cDay] = cdays
        return maxDayMap
    }

    //获取月份
    fun getMonth(maxMonth: Int): MutableList<Int> {

        val months = mutableListOf<Int>()

        for (i in 1..maxMonth) {
            months.add(i)
        }
        return months
    }

    //获取区间年份
    fun getYears(startYear: Int, endYear: Int): MutableList<Int> {
        val years = mutableListOf<Int>()
        for (i in startYear..endYear) {
            years.add(i)
        }
        return years
    }

    //获取昨天日期
    fun getYesterday(dateFormat: SimpleDateFormat): String {
        return dateFormat.format(getYesterDayCalender().time);
    }

    fun getYesterDayCalender():Calendar{
        val calender = Calendar.getInstance()
        calender.add(Calendar.DATE, -1)
        return calender
    }


    //获取今天日期
    fun getToday(dateFormat: SimpleDateFormat): String {
        return dateFormat.format(Date());
    }

    //获取本月第一天
    fun getFirstDayOfCurrentMonth(dateFormat: SimpleDateFormat): String {
        val calender = Calendar.getInstance()
        calender.add(Calendar.MONTH, 0);
        calender.set(Calendar.DAY_OF_MONTH, 1);
        return dateFormat.format(calender.time);
    }

    //获取本月最后一天
    fun getLastDayOfCurrentMonth(dateFormat: SimpleDateFormat): String {
        val calender = Calendar.getInstance()
        calender.set(Calendar.DAY_OF_MONTH, calender.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calender.time);
    }


    //获取上月最第一天
    fun getFirstDayOfLastMonth(dateFormat: SimpleDateFormat): String {
        val calender = Calendar.getInstance()
        calender.add(Calendar.MONTH, -1);
        calender.set(Calendar.DAY_OF_MONTH, calender.getActualMinimum(Calendar.DAY_OF_MONTH));
        calender.firstDayOfWeek = Calendar.MONDAY
        return dateFormat.format(calender.time);
    }

    //获取上月最后一天
    fun getLastDayOfLastMonth(dateFormat: SimpleDateFormat): String {
        val calender = Calendar.getInstance()
        calender.add(Calendar.MONTH, -1);
        calender.set(Calendar.DAY_OF_MONTH, calender.getActualMaximum(Calendar.DAY_OF_MONTH));
        calender.firstDayOfWeek = Calendar.MONDAY
        return dateFormat.format(calender.time);
    }


    /////////////////////周相关////////////////////

    //本周第一天--星期一
    fun thisWeekMonday(dateFormat: SimpleDateFormat): String {
        val calender = Calendar.getInstance()
        var dayOfWeek = calender.get(Calendar.DAY_OF_WEEK) - 1
        if (dayOfWeek == 0) {
            dayOfWeek = 7
        }
        calender.add(Calendar.DATE, -dayOfWeek + 1)
        return dateFormat.format(calender.time);
    }

    //本周周末--星期日
    fun thisWeekSunday(dateFormat: SimpleDateFormat): String {
        val calender = Calendar.getInstance()
        var dayOfWeek = calender.get(Calendar.DAY_OF_WEEK) - 1
        if (dayOfWeek == 0) {
            dayOfWeek = 7
        }
        calender.add(Calendar.DATE, -dayOfWeek + 7)
        return dateFormat.format(calender.time);
    }


    //上周第一天--星期一的时间戳
    fun lastWeekMondayDate(): Date {
        val calender = Calendar.getInstance()
        var dayOfWeek = calender.get(Calendar.DAY_OF_WEEK)
        if (dayOfWeek == 1) {
            dayOfWeek += 7
        }
        calender.add(Calendar.DATE, 2 - dayOfWeek - 7)
        return calender.time
    }

    //上周第一天--星期一
    fun lastWeekMonday(dateFormat: SimpleDateFormat): String {
        return dateFormat.format(lastWeekMondayDate());
    }

    //上周第日天--星期天
    fun lastWeekSunday(dateFormat: SimpleDateFormat): String {
        val calender = Calendar.getInstance()
        calender.time = lastWeekMondayDate()
        calender.add(Calendar.DAY_OF_WEEK, 6)
        return dateFormat.format(calender.time);
    }

    /////////////////////周相关////////////////////


    //月差

    //计算月份差是否大于3月及时间前后判断
    fun checkMonth(format: SimpleDateFormat, limit: Boolean, start: String, end: String): Boolean {

        try {
            val startDate = format.parse(start)
            val endDate = format.parse(end)

            val startCalendar = Calendar.getInstance()
            val endCalendar = Calendar.getInstance()

            startCalendar.time = startDate
            endCalendar.time = endDate

            if (startCalendar.after(endCalendar)) {
                return false
            }

            if (limit) {
                val monthCha = endCalendar[Calendar.MONTH] - startCalendar[Calendar.MONTH]
                val yearCha = (endCalendar[Calendar.YEAR] - startCalendar[Calendar.YEAR]) * 12
                val dayCha =
                    endCalendar[Calendar.DAY_OF_MONTH] - startCalendar[Calendar.DAY_OF_MONTH]
                val detalMonth = abs(monthCha + yearCha)

                //日部分超出
                if (detalMonth == 3 && dayCha > 0) {
                    Log.d("tag", "超出 3 月了 day 部分多了")
                    return false
                }

                //月部分超出
                if (detalMonth > 3) {
                    Log.d("tag", "超出 3 月了")
                    return false
                }
                Log.d("tag", "月份合理")
            }

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            //解析错误
            return false
        }

    }

    /**
     * 将年月日转为Calendar
     */
    fun transfer(year: Int, month: Int, day: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        clearDateHms(calendar)
        return calendar
    }

    /**
     * 消除时分秒
     */
    fun clearDateHms(calendar: Calendar) {
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
    }

    /**
     * 一天的开始
     */
    fun dayStart(calendar: Calendar) {
        clearDateHms(calendar)
    }

    /**
     * 一天的结束
     */
    fun dayEnd(calendar: Calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar[Calendar.MILLISECOND] = 0
    }


}
package com.caldremch.date

import java.text.SimpleDateFormat
import java.util.*

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


    //初始化好maxDay, 最大天数

    fun initMaxDaysByMap(): MutableMap<Int, MutableList<String>> {
        val maxDayMap = mutableMapOf<Int, MutableList<String>>()

        val days28 = mutableListOf<String>()
        val days29 = mutableListOf<String>()
        val days30 = mutableListOf<String>()
        val days31 = mutableListOf<String>()
        val days32 = mutableListOf<String>()

        for (i in 1..32) {
            val iStr = "${i}日"
            if (i <= 28) {
                days28.add(iStr)
            }
            if (i <= 29) {
                days29.add(iStr)
            }
            if (i <= 30) {
                days30.add(iStr)
            }
            if (i <= 31) {
                days31.add(iStr)
            }
            if (i <= 32) {
                days32.add(iStr)
            }
        }

        maxDayMap[28] = days28
        maxDayMap[29] = days29
        maxDayMap[30] = days30
        maxDayMap[31] = days31
        maxDayMap[32] = days32

        return maxDayMap
    }

    //获取月份
    fun getMonth(): MutableList<String> {
        val months = mutableListOf<String>()
        for (i in 1..12) {
            months.add("${i}月")
        }
        return months
    }

    //获取区间年份
    fun getYears(startYear: Int, endYear: Int): MutableList<String> {
        val years = mutableListOf<String>()
        for (i in startYear..endYear) {
            years.add("${i}年")
        }
        return years
    }

    //获取本周第一天
    fun getFirstDayOfCurrentWeek(dateFormat: SimpleDateFormat): String {
        val calender = Calendar.getInstance()
        calender.add(Calendar.WEEK_OF_MONTH, 0);
        calender.set(Calendar.DAY_OF_WEEK, 2);
        return dateFormat.format(calender.time);
    }

    //获取本周最后一天
    fun getLastDayOfCurrentWeek(dateFormat: SimpleDateFormat): String {
        val calender = Calendar.getInstance()
        calender.add(Calendar.DAY_OF_WEEK, calender.getActualMaximum(Calendar.DAY_OF_WEEK));
        calender.set(Calendar.DAY_OF_WEEK, 1);
        return dateFormat.format(calender.time);
    }

    //获取昨天日期
    fun getYesterday(dateFormat: SimpleDateFormat): String {
        val calender = Calendar.getInstance()
        calender.add(Calendar.DATE, -1)
        return dateFormat.format(calender.time);
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
    fun getEndDayOfLastMonth(dateFormat: SimpleDateFormat): String {
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
        if (dayOfWeek == 0){
            dayOfWeek = 7
        }
        calender.add(Calendar.DATE, -dayOfWeek+1)
        return dateFormat.format(calender.time);
    }

    //本周周末--星期日
    fun thisWeekSunday(dateFormat: SimpleDateFormat): String {
        val calender = Calendar.getInstance()
        var dayOfWeek = calender.get(Calendar.DAY_OF_WEEK) - 1
        if (dayOfWeek == 0){
            dayOfWeek = 7
        }
        calender.add(Calendar.DATE, -dayOfWeek+7)
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
        calender.time  = lastWeekMondayDate()
        calender.add(Calendar.DAY_OF_WEEK, 6)
        return dateFormat.format(calender.time);
    }

    /////////////////////周相关////////////////////



}
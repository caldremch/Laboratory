package com.caldremch.date

import kotlin.math.max

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

}
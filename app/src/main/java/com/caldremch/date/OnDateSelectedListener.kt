package com.caldremch.date

import java.util.*

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-28 15:58
 *
 * @email caldremch@163.com
 *
 * @describe 选择回调
 *
 **/
interface OnDateSelectedListener {

    fun onItemSelected(year: String, month: String, day: String){

    }

    fun onDateSelected(startDate:Date, endDate:Date){

    }
}
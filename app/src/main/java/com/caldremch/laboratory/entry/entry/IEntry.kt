package com.caldremch.laboratory.entry.entry

import android.content.Context

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-17 16:25
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
interface IEntry {
    val title: String
    fun onClick(context: Context)
}
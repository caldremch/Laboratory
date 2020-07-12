package com.caldremch.utils

import android.content.Context

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-12 17:04
 *
 * @email caldremch@163.com
 *
 * @describe 注册 Application 获得全局 context
 *
 **/
object Utils {

    private lateinit var context:Context

    fun init(context: Context){
        Utils.context = context
    }

    fun getContext():Context{
        return context
    }
}
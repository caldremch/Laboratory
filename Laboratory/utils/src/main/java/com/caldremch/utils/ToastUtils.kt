package com.caldremch.utils

import android.app.Application
import android.content.Context
import android.widget.Toast

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-10 13:53
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object ToastUtils {

    private lateinit var context: Context

    fun init(ctx: Application) {
        context = ctx.applicationContext
    }

    fun show(msg: String?) {
        msg?.let {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}
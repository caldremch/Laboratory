package com.caldremch.laboratory

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/3/19 11:20
 *
 * @description
 *
 *
 */

inline fun <reified T> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T> Context.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T> Activity.startActivity(req: Int) {
    startActivityForResult(Intent(this, T::class.java), req)
}
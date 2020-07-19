package com.caldremch.laboratory

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-31 16:12
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
abstract class BottomDialog(myContext: Context) : Dialog(myContext,R.style.DatePickerDialog) {

    protected var myContentView: View

    init {

        myContentView = LayoutInflater.from(myContext).inflate(this.getLayoutId(), null)
        this.setContentView(myContentView)

        window?.let {
            val attributes = it.attributes
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
            attributes.gravity = Gravity.BOTTOM
            it.attributes = attributes
        }

        this.iniTest()

    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun iniTest()

}
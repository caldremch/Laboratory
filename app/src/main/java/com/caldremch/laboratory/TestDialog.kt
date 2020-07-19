package com.caldremch.laboratory

import android.content.Context

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-31 16:15
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class TestDialog(myContext: Context) : BottomDialog(myContext) {

    init {
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_test
    }

    override fun iniTest() {
    }

}
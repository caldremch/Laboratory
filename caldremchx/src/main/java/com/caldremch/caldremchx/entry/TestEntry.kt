package com.caldremch.caldremchx.entry

import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-17
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/

@Entry
class TestEntry : IEntry {

    override fun getTitle(): String {
        return "test title"
    }

    override fun onClick(context: Context) {

    }

}
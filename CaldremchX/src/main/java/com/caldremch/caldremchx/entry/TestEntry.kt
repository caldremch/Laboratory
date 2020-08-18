package com.caldremch.caldremchx.entry

import com.caldremch.annotation.entry.Entry
import com.caldremch.annotation.entry.IEntry

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

    override val title: String
        get() = "test title"

    override fun onClick(context: Any) {

    }

}
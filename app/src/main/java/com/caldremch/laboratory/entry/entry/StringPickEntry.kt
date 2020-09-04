package com.caldremch.laboratory.entry.entry

import android.content.Context
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.date.StringPickDialog

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-19 14:42
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@Entry
class StringPickEntry : IEntry {
    override fun getTitle(): String {
        return "字符列表选择"
    }

    override fun onClick(context: Context) {
        context as Context
        val stringList = mutableListOf<String>(
            "标题1",
            "标题11",
            "标题111",
            "标题1111",
            "标题11111",
            "标题111111",
            "标题1111111",
            "标题11111111",
            "标题111111111",
            "标题1111111111",
            "标题11111111111",
            "标题111111111111",
            "标题1111111111111",
            "标题11111111111111",
            "标题111111111111111"
        )
        val dialog = StringPickDialog(context)
        dialog.setData(stringList)
        dialog.show()
    }
}
package com.caldremch.laboratory.ktx

import android.content.Context
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.BrowserActivity

/**
 *
 * @author Caldremch
 *
 * @date 2021/12/22
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
inline fun html(name: String, html: String): IEntry {
    return object : IEntry {
        override fun getTitle(): String {
            return name
        }
        override fun onClick(context: Context) {
            BrowserActivity.open(context, html)
        }
    }
}

inline fun <reified T> entry(t: T, crossinline c:((t:T)->Unit)):IEntry{
    return object : IEntry {
        override fun getTitle(): String {
            return T::class.java.name
        }
        override fun onClick(context: Context) {
            c.invoke(t)
        }
    }
}
inline fun <reified T> entry(crossinline c:((t:T)->Unit)):IEntry{
    return object : IEntry {
        override fun getTitle(): String {
            return T::class.java.name
        }
        override fun onClick(context: Context) {
            c.invoke(T::class.java.newInstance())
        }
    }
}
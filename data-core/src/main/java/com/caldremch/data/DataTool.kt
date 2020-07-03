package com.caldremch.data

import android.app.Application
import com.tencent.mmkv.MMKV


/**
 *
 * @author Caldremch
 *
 * @date 2020-07-03 13:53
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object DataTool {

    fun init(application: Application) {
        val rootDir: String = MMKV.initialize(application)
    }

    private fun getInstance(): MMKV {
        return MMKV.defaultMMKV()
    }
//
//    fun putString(key: String, value: String?): IData{
//       return getInstance().encode(key, value)
//    }
//    fun putStringSet(key: String, values: Set<String>): IData
//    fun putInt(key: String, value: Int): IData
//    fun putLong(key: String, value: Long): IData
//    fun putFloat(key: String, value: Float): IData
//    fun putBoolean(key: String, value: Boolean): IData
//    fun remove(key: String?): IData
//    fun clear(): IData

}
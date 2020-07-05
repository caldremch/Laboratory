package com.caldremch.data

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-03 13:55
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
interface IData {
    //查询
    fun get(key: String, defaultValue: String):String?
    fun get(key: String, defaultValue: Int): Int
    fun get(key: String, defaultValue: Long): Long
    fun get(key: String, defaultValue: Float): Float
    fun get(key: String, defaultValue: Boolean): Boolean

    //增加
    fun put(key: String, value: String?): IData
    fun put(key: String, values: Set<String>): IData
    fun put(key: String, value: Int): IData
    fun put(key: String, value: Long): IData
    fun put(key: String, value: Float): IData
    fun put(key: String, value: Boolean): IData

    //删除
    fun remove(key: String?): IData
    fun clear(): IData
}
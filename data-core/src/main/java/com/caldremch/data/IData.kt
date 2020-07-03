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
    fun put(key: String, value: String?): IData
    fun put(key: String, values: Set<String>): IData
    fun put(key: String, value: Int): IData
    fun put(key: String, value: Long): IData
    fun put(key: String, value: Float): IData
    fun put(key: String, value: Boolean): IData
    fun remove(key: String?): IData
    fun clear(): IData
}
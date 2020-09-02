package com.caldremch.laboratory.search

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-01 11:07
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
interface IExecTask {
    fun exec(data: Any? = null, delay: Long = 0)
    fun cancel()
    fun isRunning(): Boolean
}
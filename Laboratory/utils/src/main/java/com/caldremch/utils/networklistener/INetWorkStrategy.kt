package com.caldremch.utils.networklistener

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-27
 *
 * @email caldremch@163.com
 *
 * @describe 监听
 *
 **/
interface INetWorkStrategy {
    fun unRegister() //解除注册
    fun register()//注册
}
package com.caldremch.dialog.action_sheet

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/6
 *
 * @describe: 每个actionsheet都有一个引用的 数据源 ,
 * 根据数据源来进行数据填充和运行时的一些操作, 封装起来, 可以复用
 *
 * 1.是否显示 [依赖数据源]
 * 2.排序问题 [依赖数据源]
 * 3.跳转 [依赖数据源]
 *
 **/

abstract class ActionDataStrategy<T>(val data: T) {
    abstract fun createByType(data: T, type: Int, typeStr: String)
    abstract fun run(target: T)
}
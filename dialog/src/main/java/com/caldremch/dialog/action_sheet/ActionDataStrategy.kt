package com.caldremch.dialog.action_sheet

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/7/6
 *
 * @describe:
 *
 **/

abstract class ActionDataStrategy<T>(val data: T) {
    abstract fun createByType(data: T, type: Int, typeStr: String)
}
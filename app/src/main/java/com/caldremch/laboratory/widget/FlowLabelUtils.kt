package com.caldremch.laboratory.widget

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/1/28 15:54
 *
 * @description
 *
 *
 */
object FlowLabelUtils {

    fun getLineTotalWidth(list: List<SearchHistoryFlowLayout.ViewInfo>): Int {
        var lineTotalWidth = 0
        list.forEach {
            lineTotalWidth += it.childWidth
        }
        return lineTotalWidth
    }

}
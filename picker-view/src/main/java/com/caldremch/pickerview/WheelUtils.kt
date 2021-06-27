package com.caldremch.pickerview

import android.view.ViewGroup

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-27 21:27
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object WheelUtils {


    /**
     * 根据item的大小(弧的长度),和item对应的旋转角度,计算出滑轮轴的半径
     */
    fun radianToRadius(radian: Int, degree: Float): Double {
        return radian * 180.0 / (degree * Math.PI)
    }

    /**
     * 根据方向代码创建view layoutparam
     *  如果水平布局时,最好指定高度大小,  垂直布局时最定宽度大小
     */
    fun createLayoutParams(orientation: Int, size: Int): ViewGroup.LayoutParams {

        return if (orientation == WheelView.WHEEL_VERTICAL) {
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, size)
        } else {
            ViewGroup.LayoutParams(size, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }


}
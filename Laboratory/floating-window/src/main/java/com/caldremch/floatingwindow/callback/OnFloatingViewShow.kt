package com.caldremch.floatingwindow.callback

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/12 18:15
 *
 * @description
 *
 *
 */
interface OnFloatingViewShow {
    fun onShow(type:Int){}
    fun onSound(type:Int):Int
}
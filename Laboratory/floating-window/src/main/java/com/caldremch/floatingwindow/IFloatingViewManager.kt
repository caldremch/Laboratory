package com.caldremch.floatingwindow

/**
 * @author Caldremch
 * @email finishmo@qq.com
 * @date 2022/4/1 15:12
 * @description
 */
internal interface IFloatingViewManager {
    fun attach(floatingIntent: FloatingIntent) //展示新的浮窗, 或者更新浮窗内容信息
    fun attachToRecover() //浮窗的恢复, 浮窗实例已经存在
    fun detach(floatingView: AbsFloatingView) {}
    fun detach() {}
    fun destroy(){}
    fun destroy(floatingIntent: FloatingIntent){}
}
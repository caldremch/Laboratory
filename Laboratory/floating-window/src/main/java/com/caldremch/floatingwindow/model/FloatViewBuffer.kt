package com.caldremch.floatingwindow.model

import com.caldremch.floatingwindow.AbsFloatingView
import com.caldremch.floatingwindow.FloatingIntent

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/2 18:45
 *
 * @description  双缓冲
 *
 */
class FloatViewBuffer {
    fun findByIntent(floatingIntent: FloatingIntent): Node? {
        val b = front
        return if (b != null && b.intent == floatingIntent) {
            b
        }else{
            back
        }
    }

    class Node(
        val intent: FloatingIntent,
        val virtualView:AbsFloatingView
    ){
        override fun toString(): String {
            return "Node(intent=$intent, virtualView=$virtualView)"
        }
    }
    var front:Node? = null //前台窗口
    var back:Node? = null  //后台窗口
}
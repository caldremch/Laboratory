package com.caldremch.floatingwindow.model

import com.caldremch.floatingwindow.FloatingIntent

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/2 18:25
 *
 * @description
 *
 *
 */
class FloatViewLinkedList {





    private var head:Node? = null
    private var last:Node? = null

    class Node{
        var next:Node? = null
        var pre:Node? = null
        var data:FloatingIntent? = null

        constructor( data: FloatingIntent?, next: Node?, pre: Node?) {
            this.next = next
            this.pre = pre
            this.data = data
        }
    }

    fun add(data:FloatingIntent?){
        val node = Node(data, null, null)
        if(head== null){
            head = node
        }
    }

    fun getPre(data:FloatingIntent?){

    }

    fun getHead() = head

}
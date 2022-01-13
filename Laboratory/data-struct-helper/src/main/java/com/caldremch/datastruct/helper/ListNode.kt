package com.caldremch.datastruct.helper

/**
 *
 * @author Caldremch
 *
 * @date 2021/12/27
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class ListNode(var `val`: Int) {
    var next: ListNode? = null
    companion object{
        fun createByList(list:List<Int>?):ListNode?{
            var head:ListNode? = null
            var p:ListNode? = null
            list?.forEach {
                if(head == null){
                    head = ListNode(it)
                    p = head
                }else{
                    p?.next = ListNode(it)
                    p = p?.next
                }
            }
            return head
        }

        fun print(list:ListNode?){
            var p = list
            print("[")
            while(p != null){
                print(p.`val`)
                p = p.next
                if(p != null){
                    print(",")
                }
            }
            println("]")
        }

    }
}


package com.caldremch.laboratory

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 3/2/21 17:29
 *
 * @description
 *
 *
 */
class CodeLabs {

    private val threadLocalObj = object :ThreadLocal<Any>(){
        override fun initialValue(): Any {
            return Any()
        }
    }

    init {
        threadLocalObj.set("")
    }

}
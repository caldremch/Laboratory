package com.caldremch.nativelaboratory.c

/**
 *
 * @author Caldremch
 *
 * @date 2022/1/13
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object RenderActionEngine {

    var callback: ((nativePtr: RenderObject) -> Unit)? = null

    @JvmStatic
    fun onRender(nativePtr: RenderObject) {
        callback?.invoke(nativePtr)
    }

}
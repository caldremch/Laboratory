package com.caldremch.laboratory.entry.entry

import android.content.Context
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import kotlinx.coroutines.*

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-19 14:42
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@Entry
class CoroutineEntry : IEntry {

    override fun getTitle(): String {
        return "Coroutine"
    }

    private var a = 1
    private var b = 1

    override fun onClick(context: Context) {

        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }

        GlobalScope.launch(handler) {
            withContext(Dispatchers.Main) {
                println("task start1....")
            }
            withContext(Dispatchers.IO) {
                println("task start2....")
                throw RuntimeException("我是一个测试错误的")
            }
            val x = task1()
            val y = task2()
            withContext(Dispatchers.Main) {
                println("task finish")
            }
        }

    }


    private suspend fun task1() {
        withContext(Dispatchers.Default) {
            println("task1 run")
        }
    }

    private suspend fun task2() {
        withContext(Dispatchers.Default) {
            println("task2 run")
        }
    }


}
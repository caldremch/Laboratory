package com.caldremch.datastruct.helper

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DataStructHelperUnitTest {

    @Test
    fun creation() {
       val head =  ListNode.createByList(arrayListOf(1,2,4))
        ListNode.print(head)
    }
}
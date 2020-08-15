package com.caldremch.clone

import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-15
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class MockitoUnitTest {

    @Test
    fun mockListTest() {
        val mockList = mock(ArrayList::class.java)
        `when`(mockList.get(0)).thenReturn("first");
        println(mockList.get(0))
    }

}
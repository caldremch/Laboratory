package com.caldremch.clone

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.caldremch.entrylib.EntryActivity
import com.caldremch.utils.UiUtils
import com.caldremch.utils.Utils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-16
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@RunWith(AndroidJUnit4::class)
class AndroidUtilsTestCase {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().context
        Utils.init(context)

    }

    @Test
    fun lanchAt() {
        val intent = Intent(context, EntryActivity::class.java)
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    @Test
    fun testInstance() {
        println("---------" + Utils.getContext())
    }

    @Test
    fun testUtilGetString() {
        Assert.assertEquals("我是测试数据", UiUtils.getString(R.string.test_name))
    }

    @Test
    fun testUtilGetString2() {
        Assert.assertEquals("我是测试数据", UiUtils.getString(R.string.test_name))
    }


}
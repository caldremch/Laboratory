package com.caldremch.android.log

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var context: Context

    @Before
    fun useAppContext() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        CrashHandler.instance.init(context)
    }

    @Test
    fun crashHandler() {
        CrashHandler.main()
    }

}
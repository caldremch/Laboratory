package com.caldremch.laboratory

import android.app.Application
import com.caldremch.common.util.CommonLog
import com.caldremch.common.util.DefaultLogger
import com.caldremch.utils.Utils

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-12 17:14
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        CommonLog.logger = DefaultLogger()
        CommonLog.d { "好的" }
    }

}
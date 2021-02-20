package com.caldremch.laboratory

import android.app.Application
import com.caldremch.common.util.CommonLog
import com.caldremch.common.util.DefaultLogger
import com.caldremch.laboratory.register.AppRegister
import com.caldremch.utils.ActivityDelegate
import com.caldremch.utils.FileUtils
import com.caldremch.utils.ToastUtils
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
        ToastUtils.init(this)
//        CrashHandler.instance.init(this)
        FileUtils.init(this)
        Utils.init(this)
        CommonLog.logger = DefaultLogger()
        CommonLog.d { "好的" }
        ActivityDelegate.init(this)
        AppRegister.initMatrix(this)
//        ThreadStackUtils.init(this)
    }

}
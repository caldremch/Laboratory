package com.caldremch.laboratory

import android.app.Application
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
    }

}
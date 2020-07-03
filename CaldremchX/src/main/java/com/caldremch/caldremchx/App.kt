package com.caldremch.caldremchx

import android.app.Application
import com.caldremch.SimpleRequest
import com.caldremch.simplehttp.customhttp.SampleConvert2
import com.caldremch.simplehttp.customhttp.SampleObsHandler
import com.caldremch.simplehttp.customhttp.SampleServerUrlConfig
import com.caldremch.utils.ToastUtils

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-03 13:46
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
        SimpleRequest.register(SampleConvert2(), SampleObsHandler(), SampleServerUrlConfig())
    }

}
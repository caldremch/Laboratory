package com.caldremch.simplehttp.customhttp

import com.caldremch.custom.IServerUrlConfig

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-10 11:54
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class SampleServerUrlConfig : IServerUrlConfig {

    override fun enableConfig(): Boolean {
        return true
    }

    override fun currentUrl(): String {
//        return "http://192.168.2.203:3000"
        return "http://192.168.101.5:3000"
    }

    override fun defaultUrl(): String {
//        return "http://192.168.2.203:3000"
        return "http://192.168.101.5:3000"
    }
}
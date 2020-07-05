package com.caldremch.caldremchx.utils

import com.caldremch.data.DataTool

/**
 * @author Caldremch
 * @date  2020/7/5
 * @email caldremch@163.com
 * @describe
 *
 **/
object UserManager {

    private val LOGIN = "LOGIN"

    fun isLogin(): Boolean {
        return DataTool.instance.get(LOGIN, false)
    }

    fun setLogin(login: Boolean) {
        DataTool.instance.put(LOGIN, login)
    }

}
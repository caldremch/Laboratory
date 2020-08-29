package com.caldremch.caldremchx.utils

import com.caldremch.caldremchx.user.Account
import com.caldremch.caldremchx.user.Profile
import com.caldremch.data.DataTool
import com.google.gson.Gson

/**
 * @author Caldremch
 * @date  2020/7/5
 * @email caldremch@163.com
 * @describe
 *
 **/
object UserManager {

    private val gson = Gson()

    private val LOGIN = "LOGIN"
    private val TOKEN = "TOKEN"
    private val ACCOUNT = "ACCOUNT"
    private val PROFILE = "PROFILE"

    fun isLogin(): Boolean {
        return DataTool.instance.get(LOGIN, false)
    }

    fun setLogin(login: Boolean) {
        DataTool.instance.put(LOGIN, login)
    }

    fun saveAccount(account: String) {
        DataTool.instance.put(ACCOUNT, account)
    }

    fun getAccount(): Account {
        val a = DataTool.instance.get(ACCOUNT, "")!!
        return gson.fromJson(a, Account::class.java)
    }

    fun saveProfile(profile: String) {
        DataTool.instance.put(PROFILE, profile)
    }


    fun getProfile(): Profile {
        val a = DataTool.instance.get(PROFILE, "")!!
        return gson.fromJson(a, Profile::class.java)
    }

    fun saveToken(token: String) {
        DataTool.instance.put(TOKEN, token)
    }

    fun getToken(): String {
        return DataTool.instance.get(TOKEN, "")!!
    }

}
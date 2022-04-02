package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.loginmvvm.LoginFragment
import com.caldremch.laboratory.util.FragmentUtil
import com.caldremch.utils.ActivityDelegate

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-19 14:42
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@Entry
class LoginMvvmEntry : IEntry {

    override fun getTitle(): String {
        return "LoginMvvmEntry"
    }

    override fun onClick(context: Context) {
        FragmentUtil.add(context, LoginFragment())
    }

}
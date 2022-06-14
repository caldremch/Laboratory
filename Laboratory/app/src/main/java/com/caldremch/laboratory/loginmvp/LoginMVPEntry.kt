package com.caldremch.laboratory.loginmvp

import android.content.Context
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.util.FragmentUtil

/**
 * Created by Leon on 2022/6/14
 */
@Entry
class LoginMVPEntry :IEntry{
    override fun getTitle(): String {
        return this.javaClass.simpleName
    }

    override fun onClick(context: Context) {
        FragmentUtil.add(context, LoginMVPFragment())
    }
}
package com.caldremch.caldremchx.entry

import android.content.Context
import android.content.Intent
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.caldremchx.activity.MultiProcessCommunicationActivity

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-17
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/

@Entry
class SayHelloEntry : IEntry {



    override fun getTitle(): String {
        return "SayHello"
    }



    override fun onClick(context: Context) {

        context.startActivity(Intent(context, MultiProcessCommunicationActivity::class.java))
    }

}
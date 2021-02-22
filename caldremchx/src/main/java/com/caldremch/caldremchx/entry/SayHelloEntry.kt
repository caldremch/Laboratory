package com.caldremch.caldremchx.entry

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.caldremchx.activity.MainActivity
import com.caldremch.laboratory.ILaboratoryInterface

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

        context.startActivity(Intent(context, MainActivity::class.java))
    }

}
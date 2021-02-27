package com.caldremch.caldremchx.activity

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.view.View
import com.caldremch.caldremchx.R
import com.caldremch.common.base.BaseActivity
import com.caldremch.laboratory.ILaboratoryInterface
import java.util.*
import kotlin.concurrent.timer

class MainActivity : BaseActivity<Any>() {
    private val TAG = "MainActivity"

    private var laboratoryService: ILaboratoryInterface? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        val intent = Intent("com.caldremch.laboratory.LaboratoryService")
        intent.setPackage("com.caldremch.android.laboratory")
        applicationContext.bindService(intent, conn, Service.BIND_AUTO_CREATE)
    }

    fun sayHel(view: View) {
        laboratoryService!!.sayHello("我Laboratory发了一条消息")
    }

    override fun onDestroy() {
        super.onDestroy()
        applicationContext.unbindService(conn)
    }

    private val conn = LaboratoryServiceConnection()

    inner class LaboratoryServiceConnection : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            laboratoryService = ILaboratoryInterface.Stub.asInterface(service)
            Log.d(TAG, "onServiceConnected: $laboratoryService")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: ")
        }

        override fun onNullBinding(name: ComponentName?) {
            Log.d(TAG, "onNullBinding: ")
        }
    }
}
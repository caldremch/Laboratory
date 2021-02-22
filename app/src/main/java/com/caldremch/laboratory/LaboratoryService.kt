package com.caldremch.laboratory

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.caldremch.laboratory.ILaboratoryInterface

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2/22/21 11:20
 *
 * @description
 *
 *
 */
class LaboratoryService : Service() {
    private val TAG = "LaboratoryService"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder {
        return object :ILaboratoryInterface.Stub(){
            override fun sayHello(aString: String?) {
                Log.d(TAG, "sayHello收到: $aString")
            }
        }
    }
}
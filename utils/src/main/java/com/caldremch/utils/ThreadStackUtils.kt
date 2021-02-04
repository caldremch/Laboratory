package com.caldremch.utils

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

/**
 *
 * @author Caldremch
 *
 * @date 2021-02-04 14:38
 *
 * @email caldremch@163.com
 *
 * @describe print the thread stack per second
 *
 **/
object ThreadStackUtils {

    private val gson = Gson()
    private val TAG = "ThreadStackUtils"

    fun init(application: Application) {

        Timer().schedule(object : TimerTask() {
            override fun run() {
                val collection = JSONObject()
                val info: Map<Thread, Array<StackTraceElement>> = Thread.getAllStackTraces()
                info.forEach {
                    collection.put("线程名字", "${it.key.name}-${it.key.id}")
                    val array = JSONArray()
                    collection.put("堆栈信息", array)
                    it.value.forEach {
                        val obj = JSONObject()
                        obj.put("fileName", it.fileName)
                        obj.put("method", it.methodName)
                        obj.put("lineNumber", it.lineNumber)
                        array.put(obj)
                    }
                }

                Log.d(TAG, collection.toString())
            }


        }, 0, 2000)

    }
}
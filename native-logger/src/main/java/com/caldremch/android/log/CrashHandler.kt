package com.caldremch.android.log

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.os.Process
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Caldremch
 * @date 2019-06-10 10:26
 * @email caldremch@163.com
 * @describe
 */
class CrashHandler private constructor() : Thread.UncaughtExceptionHandler {
    /**
     * 系统默认UncaughtExceptionHandler处理类
     */
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null
    private var mContext: Context? = null

    /**
     * 存储设备信息和异常信息
     */
    private val mInfos: MutableMap<String, String> = HashMap()
    private val mFormater: DateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.CHINA)
    fun init(context: Context?) {
        mContext = context
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        //设置默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        if (!handleException(e) && mDefaultHandler != null) {
            mDefaultHandler!!.uncaughtException(t, e)
        } else {
            try {
                Thread.sleep(3000)
            } catch (exception: InterruptedException) {
            }
            Process.killProcess(Process.myPid())
        }
    }

    /**
     * 异常处理, 收集日志, 发送日志等
     * @param e 异常信息
     * @return true: 处理该信息, false: 未处理
     */
    private fun handleException(e: Throwable?): Boolean {
        if (e == null) {
            return false
        }
        e.printStackTrace()
        Thread(mWorkThreadRunable).start()
        //收集设备参数
        handleDeviceInfo(mContext)
        //保存到本地
        saveLocal(e)
        return true
    }

    private fun saveLocal(e: Throwable) {
        val sb = StringBuffer()
        for ((key, value) in mInfos) {
            sb.append("$key=$value\n")
        }
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        e.printStackTrace(printWriter)
        var cause = e.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        try {
            val timestamp = System.currentTimeMillis()
            val time = mFormater.format(Date())
            val fileName = ("-" + time + "-" + timestamp
                    + ".txt")
            if (Environment.getExternalStorageState() ==
                Environment.MEDIA_MOUNTED
            ) {
                val path = mContext!!.getExternalFilesDir("crash_files")?.absolutePath + "/$fileName"
                NativeLogger.saveToFile(path, sb.toString())
            }
        } catch (exception: Exception) {
            Log.d("tag", "写入文件由问题?")
            exception.printStackTrace()
            Log.e(TAG, "an error occured while writing file...", exception)
        }
    }

    private val exceptionDir: File?
        get() = mContext!!.getExternalFilesDir("crash_files")

    private fun handleDeviceInfo(context: Context?) {
        try {
            val pm = context!!.packageManager
            val pi = pm.getPackageInfo(
                context.packageName,
                PackageManager.GET_ACTIVITIES
            )
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                mInfos["versionName"] = versionName
                mInfos["versionCode"] = versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "an error occured when collect package info", e)
        }
        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                mInfos[field.name] = field[null].toString()
                Log.d(TAG, field.name + " : " + field[null])
            } catch (e: Exception) {
                Log.e(TAG, "an error occured when collect crash info", e)
            }
        }
    }

    private val mWorkThreadRunable = Runnable {
        Looper.prepare()
        Toast.makeText(mContext, "抱歉出现了异常, 收集日志中, 即将退出", Toast.LENGTH_LONG).show()
        Looper.loop()
    }

    companion object {
        private val TAG = CrashHandler::class.java.simpleName
        val instance = CrashHandler()
        fun main() {
            val fields = Build::class.java.declaredFields
            val info: MutableMap<String, String> = HashMap()
            for (field in fields) {
                try {
                    field.isAccessible = true
                    info[field.name] = field[null].toString()
                } catch (e: Exception) {
                }
            }
            println(Gson().toJson(info))
        }
    }
}
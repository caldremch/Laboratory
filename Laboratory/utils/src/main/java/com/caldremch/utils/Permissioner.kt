package com.caldremch.utils

import android.Manifest
import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.text.TextUtils
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity

/**
 *
 * @author Caldremch
 *
 * @date 2020-11-27 14:26
 *
 * @email caldremch@163.com
 *
 * @describe 获取文件读写权限, >=29 时, 添加[Manifest.permission.ACCESS_MEDIA_LOCATION]
 *
 * 同组权限,ACCESS_MEDIA_LOCATION存在如果不跟 读写权限一起申请时, 会导致下次再申请时无效(部分机型引起)
 *
 **/
class Permissioner : FragmentActivity() {

    companion object {

        private var callbacks: PermissionCallback? = null
        private val res = Manifest.permission.READ_EXTERNAL_STORAGE

        //<29 才申请 请勿为搭载 Android 10 或更高版本的设备不必要地请求存储相关权限
        private val wes = Manifest.permission.WRITE_EXTERNAL_STORAGE

        @RequiresApi(Build.VERSION_CODES.Q)
        private val aml = Manifest.permission.ACCESS_MEDIA_LOCATION

        private val PERMISSIONS = "permissions"

        private val defaultPermission = getReadWritePermission().toTypedArray()

        @JvmStatic
        fun apply(
            context: Context,
            permissions: List<String>,
            callback: PermissionCallback? = null
        ) {
            callbacks = callback
            val intent = Intent(context, Permissioner::class.java)
            intent.putStringArrayListExtra(PERMISSIONS, ArrayList(permissions))
            if (context !is Activity) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }

        @JvmStatic
        fun hasPermission(context: Context, vararg permissions: String): Boolean {
            return hasPermission(context, listOf(*permissions))
        }

        @JvmStatic
        fun hasReadWritePermission(context: Context): Boolean {
            return hasPermission(context, wes, res)
        }

        @JvmStatic
        fun hasReadWriteAndMediaLocationPermission(context: Context): Boolean {
            return hasPermission(context, getReadWritePermission())
        }

        @JvmStatic
        fun getReadWritePermission(): ArrayList<String> {
            if (Build.VERSION.SDK_INT >= 29) {
                return arrayListOf(res, aml)
            }
            return arrayListOf(res, wes)
        }

        /**
         *  [combinePermission] 需要跟读写权限一起申请的权限
         */
        @JvmStatic
        fun getReadWritePermission(vararg combinePermission: String): ArrayList<String> {
            val list = if (Build.VERSION.SDK_INT >= 29) {
                arrayListOf(res, aml)
            } else {
                arrayListOf(res, wes)
            }
            list.addAll(combinePermission)
            return list
        }

        /**
         * 获取读写权限
         * [combinePermission]  一起申请的权限
         */
        @JvmStatic
        fun getReadWritePermissionArray(vararg combinePermission: String): Array<String> {
            return getReadWritePermission(*combinePermission).toTypedArray()
        }

        @JvmStatic
        fun hasPermission(context: Context, permissions: List<String>): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true
            var opsManager: AppOpsManager? = null
            for (permission in permissions) {
                var result = context.checkPermission(permission, Process.myPid(), Process.myUid())
                if (result == PackageManager.PERMISSION_DENIED) {
                    return false
                }
                val op = AppOpsManager.permissionToOp(permission)
                if (TextUtils.isEmpty(op)) {
                    continue
                }
                if (opsManager == null) opsManager =
                    context.getSystemService(APP_OPS_SERVICE) as AppOpsManager
                result = opsManager.checkOpNoThrow(op!!, Process.myUid(), context.packageName)
                if (result != AppOpsManager.MODE_ALLOWED && result != 4) {
                    return false
                }
            }
            return true
        }
    }

    interface PermissionCallback {
        fun onGranted(grant: Boolean)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val result = it.filter { it.value.not() }
            val grand = result.isEmpty()
            callbacks?.onGranted(grand)
            callbacks = null
            finish()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        val hideView = View(this).apply { visibility = View.INVISIBLE }
        setContentView(hideView)
        val params = window.attributes
        params.x = 0
        params.y = 0
        params.height = 1
        params.width = 1
        window.attributes = params
        var p = defaultPermission
        intent?.getStringArrayListExtra(PERMISSIONS)?.let {
            p = it.toTypedArray()
        }
        launcher.launch(p)
    }


}
package com.caldremch.laboratory.entry.entry

import android.Manifest
import android.content.Context
import android.os.SystemClock
import android.util.Log
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.util.SimUtils
import com.caldremch.laboratory.util.SimUtilsEx
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import pub.devrel.easypermissions.EasyPermissions
import java.lang.StringBuilder

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
class PhoneInfoEntry : IEntry {

    private val TAG = PhoneInfoEntry::class.simpleName

    override fun getTitle(): String {
        return "手机信息"
    }

    override fun onClick(context: Context) {

        val operator1  = SimUtils.getSimOperatorName(context, 0)
        val operator2  = SimUtils.getSimOperatorName(context, 1)

        if (AndPermission.hasPermissions(context, Permission.READ_PHONE_STATE)){
            Log.d(TAG, "onClick: ${SimUtilsEx.getNetworkOperatorName(context)}")
            return
        }

        AndPermission.with(context).runtime().permission(Permission.READ_PHONE_STATE).onGranted {
            val sb = StringBuilder()
            Log.d(TAG, "onClick: ${SimUtilsEx.getNetworkOperatorName(context)}")
        }.start()
    }


}
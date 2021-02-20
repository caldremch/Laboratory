package com.caldremch.laboratory.fragment

import android.Manifest
import android.content.Intent
import android.view.View
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.utils.ToastUtils
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


/**
 *
 * @author Caldremch
 *
 * @date 2020-07-04 16:39
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class PermissionFragment : LaboratoryFragment(), EasyPermissions.PermissionCallbacks {

    private val PERMS = arrayOf<String>(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION,
    )
    var PERMISSION_STORAGE_MSG = "请授予权限，否则影响部分使用功能"
    var PERMISSION_STORAGE_CODE = 10001
    override fun getLayoutId(): Int {
        return R.layout.fragment_permission
    }

    override fun initView() {
        findViewById<View>(R.id.btn_permission).setOnClickListener {
            if (EasyPermissions.hasPermissions(requireContext(), *PERMS)) {
                // 已经申请过权限，做想做的事
                ToastUtils.show("已经有权限了")
            } else {
                // 没有申请过权限，现在去申请
                /**
                 *@param host Context对象
                 *@param rationale  权限弹窗上的提示语。
                 *@param requestCode 请求权限的唯一标识码
                 *@param perms 一系列权限
                 */
                EasyPermissions.requestPermissions(
                    this,
                    PERMISSION_STORAGE_MSG,
                    PERMISSION_STORAGE_CODE,
                    *PERMS
                );
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        ToastUtils.show("同意了")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // 用户从应用设置屏幕返回后执行某些操作， 例如再次检查权限
            ToastUtils.show("同意了-后台设置回来")
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

        // (可选)检查用户是否拒绝任何权限并选中“不再询问“
        // 这将显示一个对话框，指示用户在应用程序设置中启用权限
        // (可选)检查用户是否拒绝任何权限并选中“不再询问“
        // 这将显示一个对话框，指示用户在应用程序设置中启用权限
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            ToastUtils.show("拒绝了, 并且不在询问")
            AppSettingsDialog.Builder(this).build().show()
        }else{
            ToastUtils.show("拒绝了")
        }
    }


//    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>?, grantResults: IntArray?) {
//        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults!!)
//        //将结果转发给EasyPermissions
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
//    }

}
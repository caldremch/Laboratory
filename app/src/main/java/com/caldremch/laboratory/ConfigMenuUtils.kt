package com.caldremch.laboratory

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.Toast
import com.caldremch.dialog.owner.Contact
import com.caldremch.dialog.owner.OwnerDialog
import com.caldremch.dialog.tipDialog
import com.caldremch.dialog.utils.PhoneCheckUtils
import com.caldremch.laboratory.bean.MenuData

/**
 *
 *Created by Caldremch on 2020/6/25.
 *
 **/
object ConfigMenuUtils {

    var sOwnerDialog: OwnerDialog? = null

    fun setSetMenuData(context: Context, menuList: ArrayList<MenuData>) {
        menuList.add(MenuData().apply {
            title = "Tips Dialog"
            runnable = Runnable {
                tipDialog(context) {
                    titleText = "我是标题"
                    titleColorRes = R.color.colorPrimary
                    descText = "我是内容啊"
                    descColorStr = "#3282EF"
                    descBold = true
                    descSize = 20f
                    leftText = "取消"
                    leftBold = true
                    leftColorStr = "#3282EF"
                    rightText = "确定啊"
                    rightColorRes = R.color.colorAccent
                    gravity = Gravity.CENTER
                    widthScale = 0.74f
                    cancelOutSide = false
                    leftClick {
                        Toast.makeText(context, "点击左边了", Toast.LENGTH_SHORT).show()
                    }
                    rightClick {
                        Toast.makeText(context, "点击右边了", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        //电话列表 , 初始化数据
        var list = arrayListOf<Contact>()
        list.add(Contact().apply {
            name = "Caldremch1"
            phone = "17376999999"
            maskPhone = PhoneCheckUtils.getMaskPhone("17376999999")
            isEnable = false
        })
        list.add(Contact().apply {
            name = "我是睡"
            phone = "13676999999"
            maskPhone = PhoneCheckUtils.getMaskPhone("13676999999")
            isEnable = false
        })


        menuList.add(MenuData().apply {
            title = "Owner Dialog"
            runnable = Runnable {
                sOwnerDialog = OwnerDialog(context)
                sOwnerDialog?.maxItemCount = 2
                sOwnerDialog?.setList(list)
                sOwnerDialog?.listener = object : OwnerDialog.ConfirmListener {
                    override fun onConfirm(contacts: List<Contact>) {
                        list = contacts as ArrayList<Contact>
                    }
                }
                sOwnerDialog?.show()
            }
        })


        menuList.add(MenuData().apply {
            title = "Base Page Status"
            runnable = Runnable {
                context.startActivity(Intent(context, Main2Activity::class.java))
            }
        })
    }
}
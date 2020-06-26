package com.caldremch.laboratory

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.caldremch.dialog.owner.Contact
import com.caldremch.dialog.owner.OwnerDialog
import com.caldremch.dialog.tipDialog
import com.caldremch.laboratory.bean.MenuData
import java.util.*

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
        val list = arrayListOf<Contact>()
        list.add(Contact().apply {
            name = "Caldremch1"
            phone = "18888888888"
            isEnable = false
        })
        list.add(Contact().apply {
            name = "Caldremch2"
            phone = "17777777777"
        })

        for (x in 0..24) {
            list.add(Contact().apply {
            })
        }

        menuList.add(MenuData().apply {
            title = "Owner Dialog"
            runnable = Runnable {
                sOwnerDialog = OwnerDialog(context)
                sOwnerDialog?.setList(list)
                sOwnerDialog?.show()
            }
        })
    }
}
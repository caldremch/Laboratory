package com.caldremch.laboratory

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.caldremch.dialog.owner.ownerDialog
import com.caldremch.dialog.tipDialog
import com.caldremch.laboratory.bean.MenuData
import java.util.ArrayList

/**
 *
 *Created by Caldremch on 2020/6/25.
 *
 **/
object ConfigMenuUtils {
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

        menuList.add(MenuData().apply {
            title = "Owner Dialog"
            runnable = Runnable {
                ownerDialog(context) {

                }
            }
        })
    }
}
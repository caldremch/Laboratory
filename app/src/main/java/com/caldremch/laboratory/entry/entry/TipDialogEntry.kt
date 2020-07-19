package com.caldremch.laboratory.entry.entry

import android.content.Context
import com.caldremch.laboratory.JavaLaboratory

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
class TipDialogEntry : IEntry {
    override val title: String = "提示弹窗"
    override fun onClick(context: Context) {
        //java调用方式
        JavaLaboratory.showTipDialog(context)

//                tipDialog(context) {
//                    titleText = "我是标题"
//                    titleColorRes = R.color.colorPrimary
//                    descText = "我是内容啊"
//                    descColorStr = "#3282EF"
//                    descBold = true
//                    descSize = 20f
//                    leftText = "取消"
//                    leftBold = true
//                    leftColorStr = "#3282EF"
//                    rightText = "确定啊"
//                    rightColorRes = R.color.colorAccent
//                    gravity = Gravity.CENTER
//                    widthScale = 0.74f
//                    cancelOutSide = false
//                    leftClick {
//                        Toast.makeText(context, "点击左边了", Toast.LENGTH_SHORT).show()
//                    }
//                    rightClick {
//                        Toast.makeText(context, "点击右边了", Toast.LENGTH_SHORT).show()
//                    }
//                }
    }
}
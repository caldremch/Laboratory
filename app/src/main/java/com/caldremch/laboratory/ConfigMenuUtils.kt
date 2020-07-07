package com.caldremch.laboratory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.caldremch.dialog.action_sheet.Action
import com.caldremch.dialog.action_sheet.ActionData
import com.caldremch.dialog.action_sheet.BaseActionData
import com.caldremch.dialog.action_sheet.BaseActionSheetDialog
import com.caldremch.dialog.owner.Contact
import com.caldremch.dialog.owner.OwnerDialog
import com.caldremch.dialog.utils.PhoneCheckUtils
import com.caldremch.laboratory.action.ActionSheetDialog
import com.caldremch.laboratory.action.WeChatActionData
import com.caldremch.laboratory.bean.MenuData
import com.caldremch.laboratory.fragment.NetWatchDogFragment
import com.caldremch.laboratory.util.FragmentUtil
import com.caldremch.laboratory.widget.HouseStruct
import com.caldremch.laboratory.widget.HouseStructDialog
import com.caldremch.laboratory.widget.SimpleListener

/**
 *
 *Created by Caldremch on 2020/6/25.
 *
 **/
object ConfigMenuUtils {

    var sOwnerDialog: OwnerDialog? = null

    fun setSetMenuData(context: Context, menuList: ArrayList<MenuData>) {
        actionSheetDialog(menuList, context)
        addWatchDog(menuList, context)
        addTipDialog(menuList, context)
        addOwnerDialog(menuList, context)
        addPage(menuList, context)
        addHouseStructDialog(menuList, context)
    }

    private fun actionSheetDialog(menuList: java.util.ArrayList<MenuData>, context: Context) {

        val data = arrayListOf<BaseActionData<TargetData>>()
        val data_bottom = arrayListOf<BaseActionData<TargetData>>()
        for (x in 0..10) {
            val actionData = ActionData(
                title = "标题$x",
                imageRes = R.drawable.ic_launcher_background,
                imageUrl = null,
                msgCount = null,
                action = Action {
                    Toast.makeText(context, "标题$x", Toast.LENGTH_SHORT).show()
                },
                type = null
            )


//            data.add(actionData)
//            data_bottom.add(actionData)

        }

        data.add(WeChatActionData())

        menuList.add(MenuData().apply {
            title = "actionSheetDialog"
            runnable = Runnable {
                val dialog =
                    ActionSheetDialog(context)
                val bundle = Bundle()
                bundle.putParcelableArrayList(BaseActionSheetDialog.DATA_TOP, data)
                bundle.putParcelableArrayList(BaseActionSheetDialog.DATA_BOTTOM, data_bottom)
                dialog.arguments = bundle
                dialog.show()
            }
        })
    }

    private fun addWatchDog(menuList: java.util.ArrayList<MenuData>, context: Context) {
        menuList.add(MenuData().apply {
            title = "NetWatchDog toggle"
            runnable = Runnable {
//                val activity = context as AppCompatActivity
//                val manager = activity.supportFragmentManager
//                val tran = manager.beginTransaction()
//                tran.add(TestFragment(), "aaaa")
//                tran.commit()
                FragmentUtil.add(context, NetWatchDogFragment())
            }
        })
    }

    private fun addHouseStructDialog(
        menuList: ArrayList<MenuData>,
        context: Context
    ) {
        var houseStruct: HouseStruct? = null
        menuList.add(MenuData().apply {
            title = "House struct dialog"
            runnable = Runnable {
                val dialog = HouseStructDialog(context)
                dialog.strict = true
                val bundle = Bundle()
                bundle.putSerializable("data", houseStruct)
                dialog.arguments = bundle
                dialog.listener = object : SimpleListener<HouseStruct> {
                    override fun onData(data: HouseStruct) {
                        houseStruct = data
                        val sb = StringBuilder()
                        data.apply {
                            section?.let {
                                sb.append(it).append("室")
                            }
                            hall?.let {
                                sb.append(it).append("厅")
                            }
                            toilet?.let {
                                sb.append(it).append("卫")
                            }
                        }
                        Log.d("tag", sb.toString())
                    }
                }
                dialog.show()
            }
        })
    }

    private fun addPage(
        menuList: ArrayList<MenuData>,
        context: Context
    ) {
        menuList.add(MenuData().apply {
            title = "Base Page Status"
            runnable = Runnable {
                context.startActivity(Intent(context, PageStatusViewActivity::class.java))
            }
        })
    }

    private fun addOwnerDialog(
        menuList: ArrayList<MenuData>,
        context: Context
    ) {
        //电话列表 , 初始化数据
        var list = arrayListOf<Contact>()
        list.add(Contact().apply {
            name = "Caldremch1"
            phone = "17376999999"
            maskPhone = PhoneCheckUtils.getMaskPhone("17376999999")
        })
        list.add(Contact().apply {
            name = "我是睡"
            phone = "13676999999"
            maskPhone = PhoneCheckUtils.getMaskPhone("13676999999")
        })


        menuList.add(MenuData().apply {
            title = "Owner Dialog"
            runnable = Runnable {
                sOwnerDialog = OwnerDialog(context).apply {
                    isCanAddContact = true
                    isCanUpdateContact = false
                }
                val bundle = Bundle()
                bundle.putSerializable("data", list)
                sOwnerDialog?.arguments = bundle
                sOwnerDialog?.maxItemCount = 6
                sOwnerDialog?.listener = object : OwnerDialog.ConfirmListener {
                    override fun onConfirm(contacts: List<Contact>) {
                        list = contacts as ArrayList<Contact>
                    }
                }
                sOwnerDialog?.show()
            }
        })
    }

    private fun addTipDialog(
        menuList: ArrayList<MenuData>,
        context: Context
    ) {
        menuList.add(MenuData().apply {


            title = "Tips Dialog"
            runnable = Runnable {

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
        })
    }
}
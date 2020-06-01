package com.caldremch.date

import android.content.Context
import android.view.View
import com.caldremch.dialog.BottomDialog
import com.caldremch.laboratory.R
import com.caldremch.pickerview.callback.OnItemSelectedListener
import com.caldremch.wheel.StringAdapter

/**
 *
 * @author Caldremch
 *
 * @date 2020-05-31 18:08
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class StringPickDialog(myContext: Context) : BottomDialog(myContext) {

    val adapter by lazy { StringAdapter() }

    var listener: OnItemSelectedListener? = null

    var myIndex = 0  //默认选中第一个

    override fun getLayoutId(): Int {
        return R.layout.dialog_string_picker
    }

    override fun iniTest() {
        myContentView.findViewById<View>(R.id.wv).setOnClickListener {

        }
    }

    init {

//        val wv = myContentView.findViewById<WheelView>(R.id.wv)
//        myContentView.setBackgroundColor(Color.WHITE)
//
//        initEvent()
//
//        wv.listener = object : OnItemSelectedListener {
//            override fun onItemSelected(index: Int) {
//                myIndex = index
//            }
//        }
//        wv.setAdapter(adapter)
//
//        setCanceledOnTouchOutside(true)
    }

    private fun initEvent() {

        myContentView.findViewById<View>(R.id.tv_cancel).setOnClickListener {
            dismiss()
        }

        myContentView.findViewById<View>(R.id.tv_confirm).setOnClickListener {
            listener?.onItemSelected(myIndex)
            dismiss()
        }
    }


    fun setData(datas: List<String>) {
        adapter.data.clear()
        adapter.data.addAll(datas)
        adapter.notifyDataSetChanged()
    }
}
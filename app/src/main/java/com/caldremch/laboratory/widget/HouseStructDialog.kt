package com.caldremch.laboratory.widget

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.dialog.BaseDialog
import com.caldremch.laboratory.R

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/6/30
 *
 * @describe:
 *
 **/

class HouseStructDialog(parent: Any) : BaseDialog(parent) {


    private lateinit var rv_room: RecyclerView
    private lateinit var rv_hall: RecyclerView
    private lateinit var rv_toilet: RecyclerView

    override fun getLayoutId(): Int {
        return R.layout.dialog_house_struct
    }

    override fun initView(rootView: View) {
        rv_room = rootView.findViewById(R.id.rv_room)
        rv_hall = rootView.findViewById(R.id.rv_hall)
        rv_toilet = rootView.findViewById(R.id.rv_toilet)
    }


}
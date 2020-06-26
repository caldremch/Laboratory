package com.caldremch.dialog.owner

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.dialog.BaseDialog
import com.caldremch.dialog.R
import com.caldremch.dialog.owner.adapter.OwnerFootView
import java.lang.RuntimeException

/**
 *
 *Created by Caldremch on 2020/6/25.
 *
 **/

inline fun AppCompatActivity.ownerDialog(dsl: OwnerDialog.() -> Unit): OwnerDialog {
    val dialog = OwnerDialog(this)
    dialog.apply(dsl)
    dialog.show(this.supportFragmentManager, "OwnerDialog")
    return dialog
}

//Context dsl调用
inline fun ownerDialog(context: Context, dsl: OwnerDialog.() -> Unit): OwnerDialog {
    val dialog = OwnerDialog(context)
    dialog.apply(dsl)
    if (context is AppCompatActivity) {
        dialog.show(context.supportFragmentManager, "tipDialog")
    } else if (context is Fragment) {
        dialog.show((context as Fragment).childFragmentManager, "tipDialog")
    } else {
        throw RuntimeException("啥也不是")
    }
    return dialog
}


class OwnerDialog(var mContext: Context) : BaseDialog() {

    init {
        gravity = Gravity.BOTTOM
        widthScale = 1f
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_owner
    }

    override fun initView(rootView: View) {
        val rv = rootView.findViewById<View>(R.id.rv)
        val footerView = OwnerFootView(mContext)
        val completeTv =  rootView.findViewById<View>(R.id.tv_owner_complete)


        val adapter = OwnerAdapter()
        rv.addItemDecoration(OwnerItemDecorator())
        rv.layoutManager = LinearLayoutManager(mContext)
        adapter.addFooterView(footerView)
        rv.adapter = adapter
        val list = arrayListOf<Contact>()
        list.add(Contact().apply {
            name = "Caldremch1"
            phone = "18888888888"
        })
        list.add(Contact().apply {
            name = "Caldremch2"
            phone = "18888888888"
        })
        list.add(Contact().apply {
            name = "Caldremch2"
            phone = "18888888888"
        })
        list.add(Contact().apply {
            name = "Caldremch2"
            phone = "18888888888"
        })
        list.add(Contact().apply {
            name = "Caldremch2"
            phone = "18888888888"
        })
        list.add(Contact().apply {
            name = "Caldremch2"
            phone = "18888888888"
        })
        adapter.setList(list)
    }

}
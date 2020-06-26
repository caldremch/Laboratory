package com.caldremch.dialog.owner

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.dialog.BaseDialog
import com.caldremch.dialog.R
import com.caldremch.dialog.owner.adapter.OwnerFootView
import java.util.*

/**
 *
 *Created by Caldremch on 2020/6/25.
 *
 * 开启了 reUse, 需要注意的是, 需要传入不同的tag
 *
 **/

inline fun AppCompatActivity.ownerDialog(dsl: OwnerDialog.() -> Unit): OwnerDialog {
    val dialog = OwnerDialog(this)
    dialog.apply(dsl)
    dialog.show(this.supportFragmentManager, "OwnerDialog")
    return dialog
}

//Context dsl调用
inline fun ownerDialog(
    container: Any,
    tag: String = "OwnerDialog",
    dsl: DialogFragment.() -> Unit
): OwnerDialog {

    val dialogData = BaseDialog.checkContainer(container)
    var dialog: OwnerDialog = OwnerDialog(dialogData.context)
    dialog.apply(dsl)
    dialog.show(dialogData.fragmentManager, tag)
    return dialog
}


class OwnerDialog(context: Context, tagStr: String = "OwnerDialog") : BaseDialog(context, tagStr) {

    val adapter by lazy { OwnerAdapter() }
    val contactList = arrayListOf<Contact>()

    init {
        gravity = Gravity.BOTTOM
        widthScale = 1f
        cancelOutSide = false
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_owner
    }

    override fun initView(rootView: View) {

        val rv = rootView.findViewById<RecyclerView>(R.id.rv)
        val footerView = OwnerFootView(mContext)
        val completeTv = rootView.findViewById<View>(R.id.tv_owner_complete)

        val defaultItemAnim = DefaultItemAnimator()
        defaultItemAnim.addDuration = 300
        defaultItemAnim.removeDuration = 200
        rv.itemAnimator = defaultItemAnim

        completeTv.setOnClickListener {
            //获取 recyclerView 所有 EditText 的内容
            val a = adapter.data.toTypedArray().contentToString()
            Log.d("OwnerDialog", "initView: $a")
        }

        footerView.setOnClickListener {
            adapter.addData(Contact())
        }

        adapter.removeAllFooterView()
        rv.addItemDecoration(OwnerItemDecorator())
        rv.layoutManager = LinearLayoutManager(mContext)
        adapter.addFooterView(footerView)
        rv.adapter = adapter
    }

    fun addData(contact: Contact) {
//        contactList.add(contact)
        adapter.addData(contact)
    }

    fun setList(list: ArrayList<Contact>) {
        //deepCopy, 防止数据改变影响原数据
        val copyList = list.map { it.copy() }
        contactList.clear()
        contactList.addAll(copyList)
        adapter.setList(contactList)
    }

}
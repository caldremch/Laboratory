package com.caldremch.dialog.owner

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.dialog.BaseDialog
import com.caldremch.dialog.R
import com.caldremch.dialog.utils.PhoneCheckUtils
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator

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


    interface ConfirmListener {
        fun onConfirm(contacts: List<Contact>)
    }

    var listener: ConfirmListener? = null
    val adapter by lazy { OwnerAdapter() }
    val contactList = arrayListOf<Contact>()
    var maxItemCount = 3
    val isMaskPhone = false

    //    val footerView by lazy { OwnerFootView(mContext) }
    private lateinit var footerView: View

    private var currentItemCount = 0;

    init {
        gravity = Gravity.BOTTOM
        widthScale = 1f
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_owner
    }

    override fun initView(rootView: View) {

        val rv = rootView.findViewById<RecyclerView>(R.id.rv)
        val completeTv = rootView.findViewById<View>(R.id.tv_owner_complete)
        footerView = rootView.findViewById<View>(R.id.footerView)

        /*val defaultItemAnim = DefaultItemAnimator()
        defaultItemAnim.addDuration = 300
        defaultItemAnim.removeDuration = 200
        rv.itemAnimator = defaultItemAnim
         */

        rv.itemAnimator = ScaleInTopAnimator().apply {
            setInterpolator(OvershootInterpolator())
            removeDuration = 200
            addDuration = 200
        }

//        adapter.adapterAnimation = SlideInLeftAnimation()

        completeTv.setOnClickListener {

            //获取 recyclerView 所有 EditText 的内容
            adapter.data.map {
                //todo check phone format first
                if (isMaskPhone && TextUtils.isEmpty(it.showTitle) && !TextUtils.isEmpty(it.phone)) {
                    it.showTitle = PhoneCheckUtils.getMaskPhone(it.phone!!)
                }
            }
            listener?.onConfirm(adapter.data)
            dismiss()
        }

        footerView.setOnClickListener {
            currentItemCount++
            if (currentItemCount == maxItemCount) {
                footerView.visibility = View.GONE
            }
            adapter.addData(Contact())
        }

        adapter.listener = object : OwnerAdapter.Listener {
            override fun remove(index: Int) {
                adapter.removeAt(index)
                currentItemCount--
                if (currentItemCount < maxItemCount && adapter.footerLayoutCount == 0) {
                    footerView.visibility = View.VISIBLE
                }
            }
        }

        rv.addItemDecoration(OwnerItemDecorator())
        rv.layoutManager = LinearLayoutManager(mContext)
        handleWithSize(adapter.data.size)
        adapter.isMaskPhone = isMaskPhone
        rv.adapter = adapter
    }

    fun handleWithSize(count: Int) {
        //防止多次添加
        if (count < maxItemCount && this::footerView.isInitialized) {
            footerView.visibility = View.VISIBLE
        }
    }

    fun setList(list: List<Contact>) {
        var temp = list
        if (list.size > maxItemCount) {
            temp = list.subList(0, maxItemCount)
        }
        handleWithSize(temp.size)
        //deepCopy, 防止数据改变影响原数据
        val copyList = temp.map {
            it.copy()
        }
        currentItemCount = copyList.size
        contactList.clear()
        contactList.addAll(copyList)
        adapter.setList(contactList)
    }

}
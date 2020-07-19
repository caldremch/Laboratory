package com.caldremch.dialog.owner

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caldremch.dialog.BaseDialog
import com.caldremch.dialog.DialogAnim
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

class OwnerDialog(parent: Context) : BaseDialog(parent) {


    interface ConfirmListener {
        fun onConfirm(contacts: List<Contact>)
    }

    var listener: ConfirmListener? = null
    val adapter by lazy { OwnerAdapter() }
    var contactList: List<Contact>? = null
    var maxItemCount = 3
    val isMaskPhone = false

    var isCanAddContact = true //是否可以增加
    var isCanUpdateContact = true //是否可以修改

    private lateinit var contactAddTv: TextView
    private lateinit var rv: RecyclerView

    //    val footerView by lazy { OwnerFootView(mContext) }
    private lateinit var footerView: View

    init {
        gravity = Gravity.BOTTOM
        widthScale = 1f
        anim = DialogAnim.BOTTOM_IN_BOTTOM_OUT
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_owner
    }

    override fun initView(rootView: View) {

        arguments?.apply {
            getSerializable("data")?.let {
                contactList = it as List<Contact>
            }
        }

        rv = rootView.findViewById<RecyclerView>(R.id.rv)
        val completeTv = rootView.findViewById<View>(R.id.tv_owner_complete)
        contactAddTv = rootView.findViewById<TextView>(R.id.tv_contact_add)
        footerView = rootView.findViewById<View>(R.id.footerView)

        if (!isCanAddContact) {
            (rootView as ViewGroup).removeView(footerView)
        }

        rv.itemAnimator = ScaleInTopAnimator().apply {
            setInterpolator(OvershootInterpolator())
            removeDuration = 200
            addDuration = 200
        }



        adapter.inputListener = object : OwnerAdapter.InputListener {
            override fun onInput() {
                //检查所有item 是否有内容, 两个输入框都没填就显示可以导入
                var emptyCounts = 0
                adapter.data.forEach {
                    if (TextUtils.isEmpty(it.phone) && TextUtils.isEmpty(it.name)) {
                        emptyCounts++
                    }
                }


//                contactAddTv.isEnabled = emptyCounts > 0 || adapter.data.size < maxItemCount
//                val colorStr = if (contactAddTv.isEnabled) "#257BF4" else "#9699A0"
//                contactAddTv.setTextColor(Color.parseColor(colorStr))

                setAddContactStyle(emptyCounts > 0 || adapter.data.size < maxItemCount)


                if (!isCanAddContact) {
                    //有空的, 且小于当前长度
                    val enable = emptyCounts > 0 && emptyCounts < adapter.data.size
                    setAddContactStyle(enable)
                }
            }

        }

        contactAddTv?.setOnClickListener {

            handleAdd()

            //导入第一个全空的 item
//            adapter.data.forEachIndexed { index, item ->
//                if (TextUtils.isEmpty(item.phone) && TextUtils.isEmpty(item.name)) {
//                    adapter.data[index].phone = contact.phone
//                    adapter.data[index].name = contact.name
//                    adapter.notifyItemChanged(index)
//                    return@setOnClickListener
//                }
//            }

//            val focusIndex = adapter.currentFocusIndex
//            if (focusIndex != -1) {
//                //如果都没有值, 就覆盖
//                if (TextUtils.isEmpty(adapter.data[focusIndex].phone) && TextUtils.isEmpty(adapter.data[focusIndex].name)) {
//                    adapter.data[focusIndex].phone = contact.phone
//                    adapter.data[focusIndex].name = contact.name
//                    adapter.notifyItemChanged(focusIndex)
//                    return@setOnClickListener
//                } else {
//                    adapter.addData(contact)
//                }
//            } else {
//                adapter.addData(contact)
//            }

        }

        completeTv.setOnClickListener {

            //获取 recyclerView 所有 EditText 的内容
            adapter.data.map {
                //todo check phone format first
                if (isMaskPhone && TextUtils.isEmpty(it.maskPhone) && !TextUtils.isEmpty(it.phone)) {
                    it.maskPhone = PhoneCheckUtils.getMaskPhone(it.phone!!)
                }
            }
            listener?.onConfirm(adapter.data)
            dismiss()
        }

        footerView.setOnClickListener {
            adapter.addData(Contact())
            handleWithSize(adapter.data.size)
        }

        adapter.listener = object : OwnerAdapter.Listener {
            override fun remove(index: Int) {
                adapter.removeAt(index)
                handleWithSize(adapter.data.size)
            }
        }

        rv.addItemDecoration(OwnerItemDecorator())
        rv.layoutManager = LinearLayoutManager(mContext)
        adapter.isMaskPhone = isMaskPhone
        rv.adapter = adapter
        setList()
    }

    private fun handleAdd() {


        val contact = Contact("黄嘻嘻", "16723232323")
        adapter.data.forEachIndexed { index, item ->
            if (TextUtils.isEmpty(item.phone) && TextUtils.isEmpty(item.name)) {
                item.phone = contact.phone
                item.name = contact.name
                adapter.notifyItemChanged(index)
                return
            }
        }

        if (adapter.data.size < maxItemCount) {
            adapter.addData(contact)
        }
        handleWithSize(adapter.data.size)
    }

    fun handleWithSize(count: Int) {
        //防止多次添加
        if (count < maxItemCount) {
            if (this::footerView.isInitialized) {
                footerView.visibility = View.VISIBLE
            }
//            contactAddTv?.visibility = View.VISIBLE
//            contactAddTv.isEnabled = true
            setAddContactStyle(true)
        } else {
            if (this::footerView.isInitialized) {
                footerView.visibility = View.GONE
            }
//            contactAddTv?.visibility = View.GONE
//            contactAddTv.isEnabled = false
            setAddContactStyle(false)
        }
    }

    private fun setAddContactStyle(enable: Boolean) {
        contactAddTv.isEnabled = enable
        contactAddTv.setTextColor(Color.parseColor(if (enable) "#257BF4" else "#9699A0"))
    }

    private fun setList() {
        if (contactList != null && contactList!!.isNotEmpty()) {
            val copyList = contactList!!.map { it.copy() }
            handleWithSize(copyList.size)
            copyList.forEachIndexed { index, contact ->
            }
            adapter.setList(copyList)
        } else {
            //默认一个, 给输入
            addContact(Contact())
        }
    }

    /**
     * 添加业主电话
     */
    private fun addContact(contact: Contact) {
        contact.apply {
            if (isMaskPhone) {
//                contact.maskPhone = PhoneCheckUtils.getMaskPhone(contact.phone)
            }
        }
        adapter.addData(contact)
        handleWithSize(adapter.data.size)
        //找到第一个 EditText, 并获取焦点
        firstFocus()
    }

    private fun firstFocus() {
        rv.post {
            val view = rv.layoutManager?.findViewByPosition(0)
            view?.let {
                val editText = it.findViewById<EditText>(R.id.et_phone)
                editText?.let {
                    it.requestFocus()
                    //强行弹出键盘
                    val inputManager =
                        mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED)
                }
            }
            Log.d(TAG, "firstFocus: $view")
        }
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Log.d("tag", "onDismiss")
    }
}
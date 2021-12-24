package com.caldremch.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/**
 *
 *Created by Caldremch on 2020/6/17.
 *
 * Dialog弹窗基类, 简单功能
 *
 **/
abstract class BaseDialog private constructor() : LifeDialogFragment() {

    constructor(context: Context, tagStr: String? = null) : this() {
        this.tagStr = tagStr
        if (context !is FragmentActivity) {
            throw RuntimeException("context must be extend FragmentActivity")
        } else {
            fm = context.supportFragmentManager
        }
        mContext = context
    }

    constructor(fragment: Fragment, tagStr: String? = null) : this() {
        this.tagStr = tagStr
        fm = fragment.childFragmentManager
        mContext = fragment.context!!
    }

    private lateinit var fm: FragmentManager
    protected lateinit var mContext: Context
    private lateinit var rootView: View
    private var tagStr: String? = null
    private var parent: Any? = null //parent代表显示在哪

    /***************************************属性设置区域*********************************************/

    var gravity: Int = Gravity.CENTER //dialog位置
    var widthScale: Float = 0.75f //宽占(屏幕宽度)比
    var cancelOutSide: Boolean = true //点击弹窗以外区域是否关闭
    var isAllowingStateLoss = true //commit 是否允许状态丢失
    var anim = DialogAnim.NONE //弹窗动画
    var backPressDisable = false //点击返回是否 dismiss
    var onShowListener: DialogInterface.OnShowListener? = null
    var dismissListener: DialogInterface.OnDismissListener? = null

    /***************************************属性设置区域*********************************************/

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(getLayoutId(), container, false)
        initStyle()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(rootView)
        initData()
        initEvent()
    }

    protected open fun initEvent() {}
    protected open fun initData() {}

    //设置弹窗样式
    override fun getTheme(): Int {
        var themeId = super.getTheme()
//        when (anim) {
//            DialogAnim.BOTTOM_IN_BOTTOM_OUT -> themeId = R.style.cm_dialog_bottom_style
//        }
        return themeId
    }

    fun setClickDefaultDismiss(view: View?) {
        view?.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        //显示监听
        dialog.setOnShowListener {
            onShowListener?.onShow(it)
        }
        //按键监听
        dialog.setOnKeyListener(object : DialogInterface.OnKeyListener {
            override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return backPressDisable
                }
                return false
            }
        })
        return dialog
    }

    /**
     * 获取布局ID
     */
    protected abstract fun getLayoutId(): Int

    /**
     *  重写初始化view
     */
    open fun initView(rootView: View) {

    }

    //样式初始化
    private fun initStyle() {
        val window = dialog?.window
        dialog?.requestWindowFeature(androidx.fragment.app.DialogFragment.STYLE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(cancelOutSide)
        window?.decorView?.setPadding(0, 0, 0, 0)
        window?.attributes?.let {
            it.width = (resources.displayMetrics.widthPixels * widthScale).toInt()
            it.gravity = gravity
            window.attributes = it
        }

    }

    @Deprecated(message = "不推荐直接调用, 推荐使用show()", level = DeprecationLevel.WARNING)
    override fun show(manager: androidx.fragment.app.FragmentManager, tag: String?) {
        //isAdd 不准确, 同时添加多个事务, 并且不立即执行,
        try {
            //解决快速点时, 会有isAdded 崩溃
            manager.beginTransaction().remove(this).commitAllowingStateLoss()
            //是否AllowingStateLoss
            if (isAllowingStateLoss) {
                //反射修改
                //mDismissed = false;
                // mShownByMe = true;
                val mDismissedField = androidx.fragment.app.DialogFragment::class.java.getDeclaredField("mDismissed")
                val mShownByMeField = androidx.fragment.app.DialogFragment::class.java.getDeclaredField("mShownByMe")
                mDismissedField.isAccessible = true
                mShownByMeField.isAccessible = true
                mDismissedField.set(this, false)
                mShownByMeField.set(this, true)

                val ft = manager.beginTransaction()
                ft.add(this, tag)
                ft.commitAllowingStateLoss()
            } else {
                super.show(manager, tag)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener?.onDismiss(dialog)
    }

    fun show() {
        show(fm, tagStr)
    }

    override fun dismiss() {
        if (isAllowingStateLoss) {
            super.dismissAllowingStateLoss()
        } else {
            super.dismiss()
        }
    }

}
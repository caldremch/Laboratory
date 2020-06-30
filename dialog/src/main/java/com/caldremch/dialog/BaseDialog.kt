package com.caldremch.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 *
 *Created by Caldremch on 2020/6/17.
 *
 * Dialog弹窗基类, 简单功能
 *
 **/
abstract class BaseDialog(var parent: Any, var tagStr: String? = null) : LifeDialogFragment() {

    private lateinit var rootView: View
    var gravity: Int = Gravity.CENTER //dialog位置
    var widthScale: Float = 0.75f //宽占(屏幕宽度)比
    var cancelOutSide: Boolean = true //点击弹窗以外区域是否关闭
    var isAllowingStateLoss = true //commit 是否允许状态丢失
    protected var mContext: Context
    private var dialogData: DialogData
    var anim = DialogAnim.NONE

    init {
        dialogData = checkContainer(parent)
        mContext = dialogData.context
    }

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

    //设置弹窗样式
    override fun getTheme(): Int {
        var themeId = super.getTheme()
        when (anim) {
            DialogAnim.BOTTOM_IN_BOTTOM_OUT -> themeId = R.style.dialog_bottom_style
        }
        return themeId
    }

    //样式初始化
    private fun initStyle() {
        val window = dialog?.window
        dialog?.requestWindowFeature(DialogFragment.STYLE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(cancelOutSide)
        window?.decorView?.setPadding(0, 0, 0, 0)
        window?.attributes?.let {
            it.width = (resources.displayMetrics.widthPixels * widthScale).toInt()
            it.gravity = gravity
            window.attributes = it
        }


    }

    override fun show(manager: FragmentManager, tag: String?) {
        //isAdd 不准确, 同时添加多个事务, 并且不立即执行,
        try {
            //解决快速点时, 会有isAdded 崩溃
            manager.beginTransaction().remove(this).commit()

            //是否AllowingStateLoss
            if (isAllowingStateLoss) {
                //反射修改
                //mDismissed = false;
                // mShownByMe = true;
                try {
                    val mDismissedField = DialogFragment::class.java.getDeclaredField("mDismissed")
                    val mShownByMeField = DialogFragment::class.java.getDeclaredField("mShownByMe")
                    mDismissedField.isAccessible = true
                    mShownByMeField.isAccessible = true
                    mDismissedField.set(this, false)
                    mShownByMeField.set(this, true)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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

    fun show() {
        super.show(dialogData.fragmentManager, tagStr)
    }

    class DialogData(var context: Context, var fragmentManager: FragmentManager)

    companion object {

        fun checkContainer(target: Any): DialogData {
            if (target is AppCompatActivity) {
                return DialogData(target, target.supportFragmentManager)
            } else if (target is Fragment) {
                return DialogData(target.context!!, target.childFragmentManager)
            } else {
                throw RuntimeException("啥也不是")
            }
        }

        inline fun <reified T> find(fragmentManager: FragmentManager, tag: String): T? {
            var dialog: T? = null
            //防止同tag 名Fragment, 校验
            val fragment = fragmentManager.findFragmentByTag(tag)
            if (fragment is T) {
                dialog = fragment
            }
            return dialog
        }
    }

    override fun dismiss() {
        if (isAllowingStateLoss) {
            super.dismissAllowingStateLoss()
        } else {
            super.dismiss()
        }
    }
}
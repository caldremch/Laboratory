package com.caldremch.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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
 * 动画: 后期加 todo 7/1
 *
 **/
abstract class BaseDialog(var parent: Any, var tagStr: String) : LifeDialogFragment() {

    private lateinit var rootView: View
    var gravity: Int = Gravity.CENTER //dialog位置
    var widthScale: Float = 0.75f //宽占(屏幕宽度)比
    var cancelOutSide: Boolean = true //点击弹窗以外区域是否关闭
    protected var mContext: Context
    public var dialogData: DialogData

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
        Log.d("TAG", "onCreateView: ")
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(rootView)
        Log.d("TAG", "onViewCreated: ")
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
            manager.beginTransaction().remove(this).commit()
            super.show(manager, tag)
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
}
package com.caldremch.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment

/**
 *
 *Created by Caldremch on 2020/6/17.
 *
 * Dialog弹窗基类, 简单功能
 *
 * 动画: 后期加 todo 7/1
 *
 **/
abstract class BaseDialog : DialogFragment() {

    private lateinit var rootView: View
    var gravity: Int = Gravity.CENTER //dialog位置
    var widthScale: Float = 0.75f //宽占(屏幕宽度)比
    var cancelOutSide: Boolean = true //点击弹窗以外区域是否关闭

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
}
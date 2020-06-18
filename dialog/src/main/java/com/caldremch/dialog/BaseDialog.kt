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
 **/
abstract class BaseDialog : DialogFragment() {

    private lateinit var rootView: View

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

    protected abstract fun getLayoutId(): Int

    open fun initView(rootView: View) {

    }

    protected fun initStyle() {
        val window = dialog?.window
        dialog?.requestWindowFeature(DialogFragment.STYLE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog?.setCanceledOnTouchOutside()

        window?.decorView?.setPadding(0, 0, 0, 0)
        window?.attributes?.let {
            it.width = (resources.displayMetrics.widthPixels * 0.75).toInt()
            it.gravity = Gravity.BOTTOM
            window.attributes = it
        }

    }
}
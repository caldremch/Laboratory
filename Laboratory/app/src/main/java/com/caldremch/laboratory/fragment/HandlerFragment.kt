package com.caldremch.laboratory.fragment

import android.content.Context
import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.laboratory.databinding.FragmentHandlerBinding
import com.caldremch.laboratory.util.FragmentUtil

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-04 16:39
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class HandlerFragment : LaboratoryFragment() {

    @Entry
    class HandlerFragmentEntry : IEntry {
        override fun getTitle(): String {
            return "HandlerFragment"
        }

        override fun onClick(context: Context) {
            FragmentUtil.add(context, HandlerFragment())
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_handler
    }

    private var a = 1;

    private val binding by viewBinding(FragmentHandlerBinding::bind)

    override fun initView() {
        binding.button.setOnClickListener {
            val threadLocal = ThreadLocal<Int>()
            val threadLocalLong = ThreadLocal<Long>()
            val threadLocalDouble = ThreadLocal<Double>()
            for (i in 0..10) {
                Thread {
                    a += 1
                    threadLocal.set(a);
                    Log.d(TAG, "initView1: $a")
                    Log.d(TAG, "initView2: ${threadLocal.get()}")
                    Log.d(TAG, "")
                }.start()
            }

        }

    }

}
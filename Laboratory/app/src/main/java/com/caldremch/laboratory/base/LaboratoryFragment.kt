package com.caldremch.laboratory.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.caldremch.common.base.AbsFragment
import com.caldremch.laboratory.util.FragmentUtil
import com.hjq.bar.TitleBar

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-04 13:10
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
open class LaboratoryFragment : AbsFragment() {

    protected val TAG = this.javaClass.simpleName

//    override fun getTitleView(): View {
//        val view = TitleBar(requireContext())
//        view.setTitle(getTitle())
//        view.listener = object : TitleBar.Listener {
//            override fun leftClick() {
//                FragmentUtil.remove(context!!, this@LaboratoryFragment)
//            }
//        }
//        return view
//    }

    protected open fun getTitle(): String {
        return "默认标题"
    }

}
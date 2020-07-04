package com.caldremch.laboratory.base

import android.view.View
import com.caldremch.common.base.BaseFragment
import com.caldremch.common.widget.TitleBar
import com.caldremch.laboratory.util.FragmentUtil

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
open class LaboratoryFragment : BaseFragment<Any>() {

    override fun getTitleView(): View {
        val view = TitleBar(context!!)
        view.setTitle(getTitle())
        view.listener = object : TitleBar.Listener {
            override fun leftClick() {
                FragmentUtil.remove(context!!, this@LaboratoryFragment)
            }
        }
        return view
    }

    protected open fun getTitle(): String {
        return "默认标题"
    }

}
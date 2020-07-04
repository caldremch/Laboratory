package com.caldremch.laboratory.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.caldremch.common.base.BaseFragment
import com.caldremch.common.widget.TitleBar

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
                val manager =
                    (context!! as AppCompatActivity).supportFragmentManager.beginTransaction()
                manager.remove(this@LaboratoryFragment)
                manager.commit()
            }
        }
        return view
    }

    protected open fun getTitle(): String {
        return "默认标题"
    }

}
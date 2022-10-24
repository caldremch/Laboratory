package com.caldremch.laboratory.base

import android.view.LayoutInflater
import android.view.View
import com.caldremch.android.log.debugLog
import com.caldremch.common.base.AbsFragment
import com.caldremch.common.base.ILifeCycleLogger
import com.caldremch.common.base.ITitleInit
import com.hjq.bar.TitleBar
import com.caldremch.laboratory.R
import com.caldremch.android.common.sample.biz.TitleViewComposer

/**
 * @author Caldremch
 */
abstract class BaseFragment : AbsFragment(), ITitleInit {

    override fun getLogger(): ILifeCycleLogger {
        return object : ILifeCycleLogger {
            override fun log(tag: String, msg: String) {
                debugLog(tag) { msg }
            }
        }
    }

    override val titleView: View?
        get() = LayoutInflater.from(requireContext()).inflate(R.layout.common_titlebar_layout, null)

    override fun initTitleBar(titleView: View?) {
        titleView?.findViewById<TitleBar>(R.id.title_bar)?.let { titleBar ->
            TitleViewComposer.initTitle(titleBar, this)
        }

    }

    override fun onLeftClick(titleView: View?) {
        requireActivity().finish()
    }

}
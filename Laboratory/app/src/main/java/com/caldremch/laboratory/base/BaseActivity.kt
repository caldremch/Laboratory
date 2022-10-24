package com.caldremch.laboratory.base

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.caldremch.android.common.databinding.BR
import com.caldremch.android.common.databinding.BaseDataBindingActivity
import com.caldremch.android.common.sample.biz.TitleViewComposer
import com.caldremch.common.base.ITitleInit
import com.caldremch.laboratory.R
import com.hjq.bar.TitleBar

/**
 * Created by Leon on 2022/9/1
 */
open class BaseActivity<VB : ViewDataBinding, VM : ViewModel> : BaseDataBindingActivity<VB, VM>() , ITitleInit{

    override fun onLeftClick(titleView: View?) {
        finish()
    }

    override fun onBackPressed() {
        onLeftClick(null)
    }

    override fun initTitleBar(titleView: View?) {
        titleView?.findViewById<TitleBar>(R.id.title_bar)?.let { titleBar ->
            TitleViewComposer.initTitle(titleBar, this)
        }
    }

    override val titleView: View
        get() = LayoutInflater.from(this).inflate(R.layout.common_titlebar_layout, null)

    override fun getVariableId(): Int {
        return BR.viewModel
    }
}


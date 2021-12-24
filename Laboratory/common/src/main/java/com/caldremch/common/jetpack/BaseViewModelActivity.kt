package com.caldremch.common.jetpack

import androidx.lifecycle.ViewModelProviders
import com.caldremch.common.base.AbsActivity
import com.caldremch.common.util.Utils

/**
 *
 * @author Caldremch
 *
 * @date 2020-06-28 22:51
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class BaseViewModelActivity<VM : BaseViewModel> : AbsActivity() {
    protected val viewModel by lazy { ViewModelProviders.of(this).get(Utils.getType<VM>(this)) }
}
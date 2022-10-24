package com.caldremch.laboratory.util

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.emptyVbCallback
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.common.base.AbsFragment
import kotlin.properties.ReadOnlyProperty

/**
 *
 * @author Caldremch
 *
 * @date 2022/1/16
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/

@JvmName("viewBindingFragment")
public inline fun <F : AbsFragment, T : ViewBinding> Fragment.viewBindingFixed(
    noinline vbFactory: (View) -> T,
    noinline viewProvider: (F) -> View = AbsFragment::rootXml,
): ReadOnlyProperty<F, T> {
    return viewBinding(vbFactory, viewProvider, emptyVbCallback())
}
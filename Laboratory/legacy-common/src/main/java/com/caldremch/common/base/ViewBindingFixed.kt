package com.caldremch.common.base

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.ViewBindingProperty
import by.kirich1409.viewbindingdelegate.internal.emptyVbCallback
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.common.base.ViewBindingDelegate.iViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

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

interface ViewBindingProvider<F : Fragment, T : ViewBinding> {
    public fun <F : Fragment, T : ViewBinding> Fragment.viewBinding(
        vbFactory: (View) -> T,
        viewProvider: (F) -> View = Fragment::requireView,
        onViewDestroyed: (T) -> Unit = {},
    )
}


object ViewBindingDelegate {
    lateinit var iViewBinding: IViewBinding
}

interface IViewBinding {
    fun <F, T> getValue(
        vbFactory: (View) -> T,
        viewProvider: (F) -> View
    ): ReadOnlyProperty<F, T>
}


//@JvmName("viewBindingFragment")
//public inline fun <F : BaseFragment<*>, T : ViewBinding> Fragment.viewBindingFixed(
//    noinline vbFactory: (View) -> T,
//    noinline viewProvider: (F) -> View = BaseFragment<*>::rootXml,
//): ReadOnlyProperty<F, T> {
//    return iViewBinding.getValue(vbFactory, viewProvider)
//}


@JvmName("viewBindingFragment")
public inline fun <F : BaseFragment<*>, T : ViewBinding> Fragment.viewBindingFixed(
    noinline vbFactory: (View) -> T,
    noinline viewProvider: (F) -> View = BaseFragment<*>::rootXml,
): ReadOnlyProperty<F, T> {
    return viewBinding(vbFactory, viewProvider, emptyVbCallback())
}
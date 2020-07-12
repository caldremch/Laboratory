package com.caldremch.widget.multi

import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-12 21:45
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class MultiSelectAdapter<T, VH : BaseViewHolder> @JvmOverloads constructor(
    @LayoutRes private val layoutResId: Int,
    data: MutableList<T>? = null
) : BaseQuickAdapter<T, VH>(layoutResId, data), MultiSelectModule {



    init {
        checkModule()
    }

    private fun checkModule() {
        if (this is MultiSelectModule){
            
        }
    }

    override fun convert(holder: VH, item: T) {

    }

}
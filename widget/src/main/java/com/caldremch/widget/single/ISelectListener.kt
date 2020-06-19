package com.caldremch.widget.single

import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/6/19
 *
 * @describe:
 *
 **/

interface ISelectListener {

    interface SelectInterruptCallback {
        /**
         * goOn : 是否继续选中逻辑
         */
        fun onConsume(goOn: Boolean = false)
    }


    interface OnInterrupt {
        //拦截监听
        fun onInterrupt(
            selectedPos: Int,
            holder: RecyclerView.ViewHolder,
            currentPosition: Int,
            cacheOperation: CacheOperation<*, *>
        )
    }
}
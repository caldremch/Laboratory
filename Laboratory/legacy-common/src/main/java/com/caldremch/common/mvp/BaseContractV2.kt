package com.caldremch.common.mvp

import android.content.Context


/**
 *
 * @author Caldremch
 * @date 2019/1/24
 * @Email caldremch@163.com
 * @describe
 */
interface BaseContractV2 {

    interface BasePresenterV2<T : BaseViewV2> {

        fun attachView(view: T)

        fun detachView()

        fun retry() {

        }
    }


    interface BaseViewV2 {

        val mContext: Context

        //显示请求成功
        fun onSuccess() {}

        //失败重试
        fun onError() {}

    }

    interface BaseModelV2{
        
    }
}

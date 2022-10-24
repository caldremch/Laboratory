package com.caldremch.common.mvp

import android.content.Context


/**
 *
 * @author Caldremch
 * @date 2019/1/24
 * @Email caldremch@163.com
 * @describe
 */
interface BaseContract {

    interface BasePresenter<T : BaseView> {

        fun attachView(view: T)

        fun detachView()

        fun retry() {

        }
    }


    interface BaseView {

        val mContext: Context

        //显示请求成功
        fun onSuccess() {}

        //失败重试
        fun onError() {}

    }

    interface BaseModel{

    }
}

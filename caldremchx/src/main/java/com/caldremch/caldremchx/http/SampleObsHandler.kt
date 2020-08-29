package com.caldremch.simplehttp.customhttp

import com.caldremch.custom.IObserverHandler
import com.caldremch.exception.ApiHttpException
import com.caldremch.utils.ToastUtils

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-10 10:38
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class SampleObsHandler : IObserverHandler {

    override fun showDialog() {

    }

    override fun closeDialog() {

    }

    override fun onError(e: Throwable) {

        //todo NullDataSuccessException处理这个错误, 换成其他的提示语
        if (e is ApiHttpException) {
            ToastUtils.show(e.message)
        }
    }
}
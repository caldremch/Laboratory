package com.caldremch.nativelaboratory.c

/**
 *
 * @author Caldremch
 *
 * @date 2021/12/20
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object NativeLaboratory {

    init {
        System.loadLibrary("native-lib")
    }


    external fun startSelectServer()
    external fun startPollServer()
    external fun startEpollServer()

    external fun startSelectClient()
    external fun startPollClient()
    external fun startEpollClient()

}
package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.util.Log
import android.view.View
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.dialog.TipDialog
import com.caldremch.laboratory.entry.sample.ExceptionCodeHandlerJava
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2/25/21 09:26
 *
 * @description
 *
 *
 */
@Entry
class MultiThreadShow : IEntry {
    override fun getTitle(): String {
        return "MultiThreadShow"
    }

    override fun onClick(context: Context) {
        ExceptionCodeHandlerJava.isLogin = true
        val count = 3
        val service: ExecutorService = Executors.newFixedThreadPool(count)
        for (x in 0 until count) {
           Thread(Runnable {
               ExceptionCodeHandlerJava.handleMulti(context)
           }).start()
        }
    }
}


object ExceptionCodeHandler {

    @Volatile
    private var isShowDialog = AtomicBoolean(false)
    private var isLogin = true

    fun handleMulti(context: Context) {
        Log.d(
            "MultiEntry",
            "handleMulti thread=${Thread.currentThread().id}: isShowDialog = $isShowDialog isLogin=${isLogin} "
        )
        if (isLogin && isShowDialog.get().not()) {
            isShowDialog.set(true)
            TipDialog.Builder(context).setDescText("202弹窗")
                .setLeftText("好的")
                .setCancelOutSide(false)
                .setOnLeftBtnListener(View.OnClickListener {
                    isLogin = false
                    isShowDialog.set(false)

                })
                .build()
                .show()
        }
    }

}

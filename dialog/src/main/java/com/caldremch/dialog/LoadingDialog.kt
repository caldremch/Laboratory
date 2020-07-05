package com.caldremch.dialog

import android.app.Activity
import android.app.ProgressDialog
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import android.content.Context

/**
 * @author Caldremch
 * @date 2020-06-24 10:22
 * @email caldremch@163.com
 * @describe
 */
class LoadingDialog(context: Context?) : ProgressDialog(context), DefaultLifecycleObserver {

    override fun onDestroy(owner: LifecycleOwner) {
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }
    }

    companion object {
        private var dialog: LoadingDialog? = null
        fun show(context: Context?) {
            if (context is Activity){
                if (context.isFinishing){
                    return
                }
                dismiss()
                if (dialog == null) {
                    dialog = LoadingDialog(context)
                }
                if (context is LifecycleOwner) {
                    (context as LifecycleOwner).lifecycle.addObserver(dialog!!)
                }
                if (!dialog!!.isShowing) {
                    dialog!!.show()
                }
            }
        }

        fun dismiss() {
            if (dialog != null) {
                dialog!!.dismiss()
                dialog = null
            }
        }
    }
}
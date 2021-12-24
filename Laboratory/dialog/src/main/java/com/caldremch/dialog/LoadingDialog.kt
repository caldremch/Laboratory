package com.caldremch.dialog

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @author Caldremch
 * @date 2020-06-24 10:22
 * @email caldremch@163.com
 * @describe
 */
object LoadingDialog {

    private var dialog: Dialog? = null

    fun show(context: Context) {

        if (context is Activity) {
            if (context.isFinishing) {
                return
            }
        }

        dismiss()

        dialog = ProgressDialog(context).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }

        if (context is LifecycleOwner) {
            (context as LifecycleOwner).lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    dismiss()
                }
            })
        }

        if (!dialog!!.isShowing) {
            dialog!!.show()
        }
    }


    fun dismiss() {
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }
    }

}
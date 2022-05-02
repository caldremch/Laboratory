package com.caldremch.laboratory.mvvmdatabinding

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/5/2 20:07
 *
 * @description
 *
 *
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var userName = ObservableField<String>("")
    var loginStatus = ObservableField<Boolean>(false)

    var loginAction = View.OnClickListener {
        val name = userName.get()
        loginStatus.set(true)
    }
}
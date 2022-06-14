package com.caldremch.laboratory.loginmvp

import com.caldremch.common.mvp.BasePresenterV2

/**
 * Created by Leon on 2022/6/14
 */
class LoginPresenter(
    mView: ILoginContract.View,
    mModel: ILoginContract.Model
) : BasePresenterV2<ILoginContract.View, ILoginContract.Model>(mView, mModel),
    ILoginContract.Presenter {
    override fun login(userName: String, password: String) {
        mModel.login(userName, password).subscribe {
            mView.onLoginSuccess(userName+password)
        }
    }
}
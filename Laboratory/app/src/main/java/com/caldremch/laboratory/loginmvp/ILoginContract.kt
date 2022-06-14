package com.caldremch.laboratory.loginmvp

import com.caldremch.common.mvp.BaseContract
import com.caldremch.common.mvp.BaseContractV2
import com.caldremch.laboratory.loginmvvm.UserData
import io.reactivex.rxjava3.core.Observable

/**
 * Created by Leon on 2022/6/14
 */
interface ILoginContract {
    interface View : BaseContractV2.BaseViewV2{
        fun onLoginSuccess(msg:String)
    }
    interface Model : BaseContractV2.BaseModelV2{
        fun login(userName: String, password: String):Observable<UserData>
    }
    interface Presenter {
        fun login(userName: String, password: String)
    }
}
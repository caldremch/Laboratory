package com.caldremch.laboratory.loginmvp

import com.caldremch.laboratory.loginmvvm.UserData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe

/**
 * Created by Leon on 2022/6/14
 */
class LoginMVPModel : ILoginContract.Model {
    override fun login(userName: String, password: String): Observable<UserData> {
        return Observable.create(object :ObservableOnSubscribe<UserData>{
            override fun subscribe(emitter: ObservableEmitter<UserData>) {
                emitter.onNext(UserData("ss", "ss"))
            }

        })
    }
}
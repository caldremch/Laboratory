package com.caldremch.laboratory.loginmvp

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.scopedOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Leon on 2022/6/14
 */


val mvpModules = module {
    factory<ILoginContract.Model> { LoginMVPModel() }
    factory<ILoginContract.Presenter> { (v: ILoginContract.View) -> LoginPresenter(v, get()) }
}
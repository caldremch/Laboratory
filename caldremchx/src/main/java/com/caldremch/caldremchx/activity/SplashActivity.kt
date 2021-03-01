package com.caldremch.caldremchx.activity

import android.animation.Animator
import android.content.Context
import android.content.Intent
import com.caldremch.caldremchx.R
import com.caldremch.caldremchx.utils.UserManager
import com.caldremch.common.base.BaseActivity

class SplashActivity : BaseActivity<Any>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

//    override fun initView() {
//        lav.addAnimatorListener(object : Animator.AnimatorListener{
//            override fun onAnimationRepeat(animation: Animator?) {
//            }
//
//            override fun onAnimationEnd(animation: Animator?) {
//
//                if (UserManager.isLogin()){
//                    startActivity(Intent(mContext, MainActivity::class.java))
//                }else{
//                    startActivity(Intent(mContext, LoginActivity::class.java))
//                }
//            }
//
//            override fun onAnimationCancel(animation: Animator?) {
//            }
//
//            override fun onAnimationStart(animation: Animator?) {
//            }
//
//        })
//    }

    override fun initData() {
    }

}
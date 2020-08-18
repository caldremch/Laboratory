package com.caldremch.caldremchx.activity

import android.text.TextUtils
import com.caldremch.SimpleRequest
import com.caldremch.caldremchx.R
import com.caldremch.caldremchx.bean.AData
import com.caldremch.caldremchx.http.ApiConstant
import com.caldremch.caldremchx.utils.UserManager
import com.caldremch.callback.DialogCallback
import com.caldremch.common.base.BaseActivity
import com.caldremch.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<Any>() {


    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initEvent() {

        var adata:AData? = AData()
        adata?.age = 23;
        adata?.name = "acaldal"

        bt_login.setOnClickListener {

            adata = null
            val name = et_user_name.text.toString()
            val password = et_password.text.toString()

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                ToastUtils.show("请填写完整")
                return@setOnClickListener
            }

            SimpleRequest.post(ApiConstant.login)
                .put("email", name)
                .put("password", password)
                .execute(object : DialogCallback<HashMap<String, Any?>>(mContext) {
                    override fun onSuccess(data: HashMap<String, Any?>?) {
                        data?.let {
                            val x = 1 + 1;
                            UserManager.setLogin(true)
                            val account = data["account"]
                            val profile = data["profile"]
                            val bindings = data["bindings"]
                            val token = data["token"].toString()
                            UserManager.saveToken(token)
                            UserManager.saveAccount(account.toString())
                            UserManager.saveProfile(profile.toString())
                            finish()
                        }

                    }

                })
        }
    }

}
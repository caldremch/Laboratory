package com.caldremch.laboratory.loginmvvm

import android.util.Log
import androidx.fragment.app.viewModels
import com.caldremch.common.base.BaseFragment
import com.caldremch.common.base.viewBindingFixed
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.laboratory.databinding.LoginFragmentBinding

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-04 16:39
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class LoginFragment : LaboratoryFragment() {


    /**
     * 因为在BaseFragment又进行了一次View的包装, 所以这里的最终View的顶层不一定是xml中设置的
     */

    private val binding by viewBindingFixed(LoginFragmentBinding::bind)

    private val viewModel: LoginViewModel by viewModels()


    override fun getLayoutId(): Int {
        return R.layout.login_fragment
    }

    override fun initView() {

        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.login(userName.text.toString(), password.text.toString())
            }
        }

    }

    override fun initData() {
        viewModel.loginObs.observe(this) {
            Log.d(TAG, "initData: 登录成功: $it")
        }
    }

}
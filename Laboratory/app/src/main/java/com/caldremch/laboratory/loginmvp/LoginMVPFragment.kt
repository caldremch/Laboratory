package com.caldremch.laboratory.loginmvp

import android.util.Log
import android.widget.Toast
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.laboratory.databinding.LoginFragmentBinding
import com.caldremch.laboratory.util.viewBindingFixed
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

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
class LoginMVPFragment : LaboratoryFragment(), ILoginContract.View {


    private val presenter:ILoginContract.Presenter by inject{ parametersOf(this)}

    /**
     * 因为在BaseFragment又进行了一次View的包装, 所以这里的最终View的顶层不一定是xml中设置的
     */

    private val binding by viewBindingFixed(LoginFragmentBinding::bind)


    override fun onLoginSuccess(msg: String) {
        Toast.makeText(requireContext(), "$msg", Toast.LENGTH_SHORT).show()
        getKoin().logger.debug("登录成功:$msg")
    }

    override fun onSuccess() {

    }

    override fun onError() {

    }


    override val layoutId: Int
        get() = R.layout.login_fragment

    override fun initView() {

        binding.apply {
            btnLogin.setOnClickListener {
                presenter.login("caldremch", "caldremch")
                Log.d(TAG, "initView: ${
                    getKoin()
                }")
            }
        }

    }

    override fun initData() {

    }

}
package com.caldremch.laboratory.mvvmdatabinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.laboratory.databinding.LoginMvvmDatabindingFragmentBinding

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
class LoginMVVMDataBindingFragment : LaboratoryFragment() {


    /**
     * 因为在BaseFragment又进行了一次View的包装, 所以这里的最终View的顶层不一定是xml中设置的
     */

    private lateinit var dataBinding:LoginMvvmDatabindingFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.login_mvvm_databinding_fragment, container, false)
        return dataBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataBinding.unbind()
    }

    private val store = ViewModelStore()

    private val model:LoginViewModel by viewModels()

    override fun initView() {
        //看, 这里是数据的来源, 这里仅仅是使用了DataBinding, 而如果要使用MVVM的, 数据获取不在这里
        dataBinding.userInfo = User("caldremch", "123456")

    }

    override fun initData() {
//        viewModel.loginObs.observe(this) {
//            Log.d(TAG, "initData: 登录成功: $it")
//        }
    }

}
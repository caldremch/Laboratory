package com.caldremch.laboratory.navigation

import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.common.base.AbsBaseFragment
import com.caldremch.laboratory.R
import com.caldremch.laboratory.databinding.FragmentNavigationLoginMainBinding

/**
 * Created by Leon on 2022/6/13
 */
class NavigationLoginMainFragment : AbsBaseFragment() {


    private val binding by viewBinding(FragmentNavigationLoginMainBinding::bind)

    override fun getLayoutId(): Int {
        return R.layout.fragment_navigation_login_main
    }

    override fun initView() {
        binding.btnLogin.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_login)
        }

        binding.btnRegister.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_register, bundleOf("btnName" to "我是传递的"))
        }
    }
}
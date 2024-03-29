package com.caldremch.laboratory.navigation

import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.common.base.AbsFragment
import com.caldremch.laboratory.R
import com.caldremch.laboratory.databinding.FragmentNavigationLoginBinding

/**
 * Created by Leon on 2022/6/13
 */
class NavigationLoginFragment : AbsFragment() {


    private val binding by viewBinding(FragmentNavigationLoginBinding::bind)

    override val layoutId: Int
        get() = R.layout.fragment_navigation_login

    override fun initView() {
        binding.btnLogin.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_login_to_login_main)
        }


    }
}
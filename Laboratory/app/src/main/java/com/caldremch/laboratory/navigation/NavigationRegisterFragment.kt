package com.caldremch.laboratory.navigation

import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.common.base.AbsFragment
import com.caldremch.laboratory.R
import com.caldremch.laboratory.databinding.FragmentNavigationRegisterBinding

/**
 * Created by Leon on 2022/6/13
 */
class NavigationRegisterFragment : AbsFragment() {


    private val binding by viewBinding(FragmentNavigationRegisterBinding::bind)

    override val layoutId: Int
        get() = R.layout.fragment_navigation_register

    override fun initView() {
        binding.btnRegister.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_register_to_login_main2)
        }

        binding.btnRegister.text = arguments?.getString("btnName")


    }
}
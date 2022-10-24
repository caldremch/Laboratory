package com.caldremch.laboratory.navigation

import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.common.base.AbsFragment
import com.caldremch.laboratory.R
import com.caldremch.laboratory.databinding.FragmentNavigationLoginMainBinding
import org.koin.android.ext.android.getKoin
import org.koin.core.logger.Level

/**
 * Created by Leon on 2022/6/13
 */
class NavigationLoginMainFragment : AbsFragment() {


    private val binding by viewBinding(FragmentNavigationLoginMainBinding::bind)

    override val layoutId: Int
        get() = R.layout.fragment_navigation_login_main

    override fun initView() {

        println("试一下?")
        getKoin().logger.log(Level.ERROR, "假假按揭啊")

        binding.btnLogin.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_login)
        }

        binding.btnRegister.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_register, bundleOf("btnName" to "我是传递的"))
        }
    }
}
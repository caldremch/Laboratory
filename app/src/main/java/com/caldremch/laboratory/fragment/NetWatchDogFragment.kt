package com.caldremch.laboratory.fragment

import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.laboratory.databinding.FragmentWatchDogBinding
import com.caldremch.utils.networklistener.ConnectInfo
import com.caldremch.utils.networklistener.ListenerType
import com.caldremch.utils.networklistener.NetListener
import com.caldremch.utils.networklistener.NetWatchDog

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-03 17:51
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class NetWatchDogFragment() : LaboratoryFragment() {

    private val binding by viewBinding(FragmentWatchDogBinding::bind)

    override fun getTitle(): String {
        return "网络监听"
    }

    val netWatchDog by lazy {
        NetWatchDog(this,
            viewLifecycleOwner,
            ListenerType.WIFI,
            object : NetListener {
                override fun onStatus(info: ConnectInfo) {
                    binding.tv.text = "type:${info.typeName} status: ${info.state.name}"
                    Log.d(
                        "NetWatchDog",
                        "onStatus: type:${info.typeName} status: ${info.state.name} "
                    )
                }
            }
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_watch_dog
    }

    override fun initData() {
        netWatchDog.register()
        netWatchDog.register()
        netWatchDog.register()
    }

    override fun initEvent() {
        binding.sc.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                netWatchDog.register()
            } else {
                netWatchDog.unRegister()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        if (sc.isChecked) {
//            netWatchDog.stop()
//        }

    }
}
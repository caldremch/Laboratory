package com.caldremch.laboratory.fragment

import android.util.Log
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.utils.networklistener.ConnectInfo
import com.caldremch.utils.networklistener.ListenerType
import com.caldremch.utils.networklistener.NetListener
import com.caldremch.utils.networklistener.NetWatchDog
import kotlinx.android.synthetic.main.fragment_watch_dog.*

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

    override fun getTitle(): String {
        return "网络监听"
    }

    val netWatchDog by lazy {
        NetWatchDog(this,
            viewLifecycleOwner,
            ListenerType.WIFI,
            object : NetListener {
                override fun onStatus(info: ConnectInfo) {
                    tv.text = "type:${info.typeName} status: ${info.state.name}"
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
        sc.setOnCheckedChangeListener { buttonView, isChecked ->
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
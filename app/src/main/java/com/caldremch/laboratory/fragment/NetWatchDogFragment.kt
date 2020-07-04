package com.caldremch.laboratory.fragment

import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment
import com.caldremch.utils.NetWatchDog
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
        return "wifi相关"
    }

    val netWatchDog by lazy { NetWatchDog(context!!) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_watch_dog
    }

    override fun initData() {

    }

    override fun initEvent() {
        sc.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                netWatchDog.start()
            } else {
                netWatchDog.stop()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (sc.isChecked) {
            netWatchDog.stop()
        }

    }
}
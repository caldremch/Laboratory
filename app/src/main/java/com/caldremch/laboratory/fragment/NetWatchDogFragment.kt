package com.caldremch.laboratory.fragment

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.caldremch.common.base.BaseFragment
import com.caldremch.common.widget.TitleBar
import com.caldremch.laboratory.R
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
class NetWatchDogFragment() : BaseFragment<Any>() {

    val netWatchDog by lazy { NetWatchDog(context!!) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_watch_dog
    }

    override fun getTitleView(): View {
        val view = TitleBar(context!!)
        view.listener = object : TitleBar.Listener {
            override fun leftClick() {
                val manager =
                    (context!! as AppCompatActivity).supportFragmentManager.beginTransaction()
                manager.remove(this@NetWatchDogFragment)
                manager.commit()
            }
        }
        return view
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
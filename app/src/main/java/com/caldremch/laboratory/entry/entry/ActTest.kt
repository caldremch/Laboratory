package com.caldremch.laboratory.entry.entry

import com.caldremch.common.base.BaseActivity
import com.caldremch.laboratory.R
import kotlinx.android.synthetic.main.acti_saa.*

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-29 16:00
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class ActTest : BaseActivity<Any>() {
    private val TAG = "ActTest"

    override fun getLayoutId(): Int {
        return R.layout.acti_saa
    }

    override fun initView() {
        printAttachInfo()
    }

    private fun printAttachInfo() {
        fl.post {
            //https://www.cnblogs.com/dasusu/p/8047172.html
        }
    }

    override fun onResume() {
        super.onResume()
        printAttachInfo()
    }

}
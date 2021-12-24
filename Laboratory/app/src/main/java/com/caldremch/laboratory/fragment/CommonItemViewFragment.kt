package com.caldremch.laboratory.fragment

import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment

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
class CommonItemViewFragment() : LaboratoryFragment() {

    override fun getTitle(): String {
        return "CommonItemView Demo"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_common_item_view
    }

    override fun initData() {

    }

    override fun initEvent() {

    }

}
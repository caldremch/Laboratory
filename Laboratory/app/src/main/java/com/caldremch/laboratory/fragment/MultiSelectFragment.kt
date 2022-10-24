package com.caldremch.laboratory.fragment

import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.LaboratoryFragment

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-12 22:11
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class MultiSelectFragment : LaboratoryFragment() {
    override fun getTitle(): String {
        return "多选"
    }

    override val layoutId: Int
        get() = R.layout.fragment_multi_selected
}
package com.caldremch.laboratory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.caldremch.laboratory.R

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-03 23:09
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class TestFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.test_title, container, false)
    }
}
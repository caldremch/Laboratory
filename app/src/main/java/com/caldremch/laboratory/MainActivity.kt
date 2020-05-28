package com.caldremch.laboratory

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.caldremch.pickerview.Utils
import com.caldremch.pickerview.adapter.SimpleWheelAdapter
import com.caldremch.wheel.TestAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Utils.init(this)
        wv.setAdapter(TestAdapter())
    }

    //itemCount 对不上, 空白展示效果-
    fun start(view: View) {
        wv.setAdapter(TestAdapter())

//        val layoutParams = rv2.layoutParams
//        layoutParams.height = (2 * 4 + 1) * 90
//        rv2.layoutParams = layoutParams
//
//
//
//
//        //撑开部分是 空白 view 导致的
//        rv2.layoutManager = LinearLayoutManager(this)
//        val simpleWheelAdapter = SimpleWheelAdapter(TestAdapter(), 1, 90, 4)
//        rv2.adapter = simpleWheelAdapter


    }

    fun tost(view: View) {

    }


}

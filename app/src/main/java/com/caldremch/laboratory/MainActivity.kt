package com.caldremch.laboratory

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv.text = 1.toString()
        var x:Int? = null
        tv.text = "$x"
        tv.text = x?.toString()
        sv.setOnClickListener {
            sv.startAnim(104f)
        }
    }

    fun start(view: View) {

    }

    fun tost(view: View) {

    }


}

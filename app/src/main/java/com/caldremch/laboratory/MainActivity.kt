package com.caldremch.laboratory

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.caldremch.pickerview.Utils
import com.caldremch.pickerview.callback.OnItemSelectedListener
import com.caldremch.wheel.TestAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Utils.init(this)

        val context = this
//        wv.post {
//            wv.setAdapter(TestAdapter())
//        }
//
//        wv.listener = object : OnItemSelectedListener{
//            override fun onItemSelected(index: Int) {
//                Toast.makeText(context, "index = $index", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    fun start(view: View) {
        wv.setAdapter(TestAdapter())
        dpv.refresh()
    }

    fun tost(view: View) {

    }


}

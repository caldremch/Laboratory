package com.caldremch.laboratory

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.caldremch.date.OnDateSelectedListener
import com.caldremch.pickerview.Utils
import com.caldremch.pickerview.callback.OnItemSelectedListener
import com.caldremch.wheel.StringAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Utils.init(this)

        val context = this

        wv.post {

            val stringList = mutableListOf<String>("超强","我只看优秀","只看中等","我啥都要","不可能不可能不可能","超强","我只看优秀","只看中等","我啥都要","不可能不可能不可能")
            val adapter = StringAdapter()
            adapter.data.clear()
            adapter.data.addAll(stringList)
            wv.setAdapter(adapter)
        }

        wv.listener = object : OnItemSelectedListener{
            override fun onItemSelected(index: Int) {
                Toast.makeText(context, "index = $index", Toast.LENGTH_SHORT).show()
            }
        }

        dpv.listener = object : OnDateSelectedListener{
            override fun onItemSelected(dateStr: String) {
                tvDate.text = dateStr
            }

        }
    }

    fun start(view: View) {
        wv.setAdapter(StringAdapter())
        dpv.refresh()
    }

    fun tost(view: View) {

    }


}

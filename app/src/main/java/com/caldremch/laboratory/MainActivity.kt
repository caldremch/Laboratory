package com.caldremch.laboratory

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.caldremch.date.DatePickDialog
import com.caldremch.date.OnDateSelectedListener
import com.caldremch.date.StringPickDialog
import com.caldremch.dialog.TestDialog
import com.caldremch.dialog.tipDialog
import com.caldremch.pickerview.callback.OnItemSelectedListener
import com.caldremch.wheel.StringAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var simpleDateFormat = SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.CHINA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context = this


        wv.post {

            val stringList = mutableListOf<String>(
                "超强",
                "我只看优秀",
                "只看中等",
                "我啥都要",
                "不可能不可能不可能",
                "超强",
                "我只看优秀",
                "只看中等",
                "我啥都要",
                "不可能不可能不可能"
            )
            stringPickDialog.setData(stringList)
            stringPickDialog.listener = object : OnItemSelectedListener {
                override fun onItemSelected(index: Int) {
                    Log.d("tag", "index=$index")
                }

            }
            val adapter = StringAdapter()
            adapter.data.clear()
            adapter.data.addAll(stringList)
            wv.setAdapter(adapter)
        }

        wv.listener = object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                Toast.makeText(context, "index = $index", Toast.LENGTH_SHORT).show()
            }
        }

        dpv.listener = object : OnDateSelectedListener {
            override fun onItemSelected(year: Int, month: Int, day: Int) {
//                tvDate.text = year
            }

        }
    }

    val dialog by lazy {
        DatePickDialog(this)
    }
    val testDialg by lazy {
        TestDialog(this)
    }

    val stringPickDialog by lazy {
        StringPickDialog(this)
    }

    var i = 1;

    fun start(view: View) {

//        tipDialog(supportFragmentManager){
//
//        }


        tipDialog {
            negativeText = "取消"
            negativeColor = "#3282EF"
        }

//        val dialog = TipDialog()
//        dialog.show(supportFragmentManager, "tag")

//        tipDialog {
//
//        }

//        val flag = "60,80,0,90"
//        val flagDesc = "60(达标),80(优秀),0,90"
//        sv.setScoreAndDesc(90f, flag, flagDesc)
//        sv.post {
//            Log.d("tag","width=${sv.width},height=${sv.height}")
//
//        }
//        sv.setTitle("9.8", "昨日得分")
//        sv.startAnim(80f)

//
//       val dialog = DatePickDialog(this)
//        if (!dialog.isShowing) {
//            dialog.listener = object : OnDateSelectedListener{
//                override fun onItemSelected(year: Int, month: Int, day: Int) {
//
//
//                }
//
//                override fun onDateSelected(startDate: Date, endDate: Date) {
//                    Log.d("tag", "开始时间: ${simpleDateFormat.format(startDate)}")
//                    Log.d("tag","结束时间: ${simpleDateFormat.format(endDate)}")
//                }
//            }
//            dialog.show()
//            dialog.limit
//            dialog.setDate(DatePickDialog.YESTERDAY)
//        }

    }

    fun tost(view: View) {

    }

    fun setDatess(view: View) {
        var stringPickDialog = StringPickDialog(this)
        if (!stringPickDialog.isShowing) {
            stringPickDialog.show()
        }
    }


}

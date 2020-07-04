package com.caldremch.laboratory

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.caldremch.laboratory.fragment.NetWatchDogFragment

class PageStatusViewActivity : AppCompatActivity() {

//    override fun getLayoutId(): Int {
//        return
//    }
//
//    override fun getTitleViewId(): Int {
//        return R.layout.test_title
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    fun addFr(view: View) {
        Log.d("TAG", "addFr: start add")
        val manager = supportFragmentManager.beginTransaction()
//        manager.add(R.id.fl, TestFragment())
        val f = NetWatchDogFragment().apply {
            title = "网络监听"
        }
        manager.add(android.R.id.content, f)
        manager.commit()
    }


}

package com.caldremch.laboratory

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.caldremch.laboratory.fragment.NetWatchDogFragment
import kotlinx.android.synthetic.main.activity_main2.*

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
        val tv = TextView(this)
        tv.text = "圣诞节阿里街道啦"
        ll_root.addView(tv, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        Handler().postDelayed(Runnable {
            ll_root.removeView(tv)
            Log.d("TAG", "addFr: $tv")
        }, 3000)
    }


}

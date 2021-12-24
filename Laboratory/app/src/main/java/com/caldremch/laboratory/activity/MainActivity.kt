package com.caldremch.laboratory.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.caldremch.common.base.BaseActivity

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/3/19 11:17
 *
 * @description
 *
 *
 */
class OpenActEvent {

}

class MainActivity : BaseActivity<Any>() {

//    override fun isUseEvent(): Boolean {
//        return true
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, PageListActivity::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("onActivityResult", "onActivityResult=$requestCode $resultCode")
        finish()
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun openNew(event: OpenActEvent) {
//        val intent = Intent(this, PageListActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        applicationContext.startActivity(intent)
//    }
}
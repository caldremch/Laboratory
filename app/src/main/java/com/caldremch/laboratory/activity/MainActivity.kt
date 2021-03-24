package com.caldremch.laboratory.activity

import android.content.Intent
import android.os.Bundle
import com.caldremch.common.base.BaseActivity
import com.caldremch.laboratory.startActivity
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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

    override fun isUseEvent(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity<PageListActivity>()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun openNew(event: OpenActEvent) {
        val intent = Intent(this, PageListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        applicationContext.startActivity(intent)
    }
}
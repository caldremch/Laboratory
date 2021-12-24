package com.caldremch.laboratory

import android.content.Context
import android.content.Intent
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.common.base.BaseActivity
import com.caldremch.laboratory.databinding.ActivityBrowserBinding

/**
 *
 * @author Caldremch
 *
 * @date 2021/12/20
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class BrowserActivity : BaseActivity<Any>(){

    private val binding by viewBinding(ActivityBrowserBinding::bind)

    override fun getLayoutId(): Int {
        return R.layout.activity_browser
    }

    override fun initView() {
        binding.webView.apply {
            loadUrl(intent.getStringExtra("url")!!)
        }
    }

    companion object{
        fun open(context:Context, url:String){
            val intent = Intent(context,BrowserActivity::class.java)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }
}
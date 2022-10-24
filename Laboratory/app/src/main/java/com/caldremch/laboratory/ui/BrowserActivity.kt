package com.caldremch.laboratory.ui

import android.content.Context
import android.content.Intent
import com.caldremch.laboratory.R
import com.caldremch.laboratory.base.BaseActivity
import com.caldremch.laboratory.databinding.ActivityBrowser2Binding
import com.caldremch.laboratory.viewmodel.BrowserViewModel

class BrowserActivity : BaseActivity<ActivityBrowser2Binding,BrowserViewModel>() {
     override val layoutId: Int
        get() = R.layout.activity_browser2

    companion object{
        fun open(context: Context, url:String){
            val intent = Intent(context, BrowserActivity::class.java)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }
}
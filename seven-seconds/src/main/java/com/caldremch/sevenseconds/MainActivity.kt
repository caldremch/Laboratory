package com.caldremch.sevenseconds

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btn).setOnClickListener {

        }
    }

    fun click1(view: View) {
        startActivity(Intent(this, MainActivityCalendar::class.java))
    }

    fun click2(view: View) {
        startActivity(Intent(this, MainActivityCalendar::class.java))
    }
}
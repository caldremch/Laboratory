package com.caldremch.sevenseconds

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.date.picker.panel.DatePickerPanelView
import java.text.SimpleDateFormat

class MainActivityCalendar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_cal)
        val panel = findViewById<DatePickerPanelView>(R.id.date_picker_panel)
        panel.setCallback {
            val str = SimpleDateFormat("yyyy年MM月dd日").format(it)
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }
    }
}
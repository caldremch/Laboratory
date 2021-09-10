package com.caldremch.sevenseconds

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.date.picker.panel.DatePickerPanelView

class MainActivityCalendar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_cal)
        val panel = findViewById<DatePickerPanelView>(R.id.date_picker_panel)
    }
}
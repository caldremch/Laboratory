package com.caldremch.sevenseconds

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.date.picker.panel.DatePickerPanelUtils
import com.date.picker.panel.DatePickerPanelView
import java.text.SimpleDateFormat
import java.util.*

class MainActivityCalendar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_cal)
        val panel = findViewById<DatePickerPanelView>(R.id.date_picker_panel)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, 8)
        calendar.set(Calendar.DAY_OF_MONTH, 21)
        DatePickerPanelUtils.clearHMS(calendar)
        panel.setLimitType(1);
        panel.setSelectedDate(1633680188294)
        val format = SimpleDateFormat("yyyy年MM月dd日")
        Log.d("MainActivityCalendar", "onCreate: ${format.format(calendar.timeInMillis)}")
        panel.setCallback {
            val str = format.format(it)
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }
    }
}
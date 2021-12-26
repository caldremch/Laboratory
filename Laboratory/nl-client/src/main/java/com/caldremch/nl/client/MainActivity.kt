package com.caldremch.nl.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.caldremch.nativelaboratory.c.NativeLaboratory
import com.caldremch.nl.client.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding.tvStartServer.setOnClickListener {
            NativeLaboratory.startSelectClient()
        }
    }
}
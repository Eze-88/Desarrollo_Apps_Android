package com.framus.a00_muestro_text

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cartel: TextView = findViewById(R.id.cartel)
        cartel.text = "Hola Eze"
    }
}
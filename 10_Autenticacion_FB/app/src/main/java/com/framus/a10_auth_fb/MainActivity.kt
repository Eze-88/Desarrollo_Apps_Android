package com.framus.a10_auth_fb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Todas las acciones se hacen en los fragmentos:
        // - Pantalla de logeo
        // - CmbContra
        // - BajaUsr
    }
}
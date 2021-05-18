package com.framus.a07_tabs_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)

        Glide
            .with(applicationContext)
            .load("https://dbdzm869oupei.cloudfront.net/img/vinylrugs/preview/18784.png")
            .centerInside()
            .into(findViewById(R.id.imageView))
    }
}
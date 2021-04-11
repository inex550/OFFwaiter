package com.example.coffwaiter.ui.ready_order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.coffwaiter.R
import com.example.coffwaiter.ui.start.StartActivity

class ThankYouPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you_page)

        Handler().postDelayed({
            val intent = Intent(this,StartActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }
}

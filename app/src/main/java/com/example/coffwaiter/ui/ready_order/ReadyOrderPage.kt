package com.example.coffwaiter.ui.ready_order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.coffwaiter.R

class ReadyOrderPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready_order_page)
        val btn_got = findViewById<Button>(R.id.btn_got)

        btn_got.setOnClickListener{
            val intent = Intent(this, ThankYouPage::class.java)
            startActivity(intent)
            finish()
        }
    }
}
package com.example.coffwaiter.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffwaiter.databinding.ActivityStartBinding
import com.example.coffwaiter.ui.qr_scan.QrScanActivity

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.qrScanBtn.setOnClickListener {
            val qrScanIntent = Intent(this, QrScanActivity::class.java)
            startActivity(qrScanIntent)
        }
    }
}
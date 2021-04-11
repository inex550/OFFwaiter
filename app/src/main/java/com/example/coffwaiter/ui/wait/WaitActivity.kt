package com.example.coffwaiter.ui.wait

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.coffwaiter.GlobalData
import com.example.coffwaiter.R
import com.example.coffwaiter.databinding.ActivityWaitBinding
import com.example.coffwaiter.models.Order
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class WaitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWaitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ProcessingWaitFragment())
            .commit()
    }

    fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
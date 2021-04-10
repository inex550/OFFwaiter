package com.example.coffwaiter.ui.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffwaiter.databinding.ActivityRestaurantBinding
import com.example.coffwaiter.dialogs.InfoDialog
import com.example.coffwaiter.models.Restaurant
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.NullPointerException

class RestaurantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantBinding

    val db = Firebase.firestore

    private var foodsAdapter = FoodsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.shimmerSfl.visibility = View.VISIBLE

        binding.foodListRv.layoutManager = LinearLayoutManager(this)
        binding.foodListRv.adapter = foodsAdapter

        val rid = intent.getStringExtra("rid")!!

        db.collection("restaurants").document(rid).get()
            .addOnSuccessListener {

                try {
                    val restaurant = it.toObject<Restaurant>()

                    val foods = restaurant?.foods!!

                    if (foods.isNotEmpty()) {
                        foodsAdapter.foods = foods
                        binding.shimmerSfl.visibility = View.GONE
                    }
                } catch (e: NullPointerException) {
                    restaurantNotFound()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Ошибка при получении данных", Toast.LENGTH_SHORT).show()
                it.printStackTrace()
            }
    }

    private fun restaurantNotFound() {
        Toast.makeText(applicationContext, "QR-код не зарегистрирован", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerSfl.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerSfl.stopShimmer()
    }
}
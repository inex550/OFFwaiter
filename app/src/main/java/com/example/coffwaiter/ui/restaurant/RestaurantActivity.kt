package com.example.coffwaiter.ui.restaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffwaiter.GlobalData
import com.example.coffwaiter.databinding.ActivityRestaurantBinding
import com.example.coffwaiter.dialogs.InfoDialog
import com.example.coffwaiter.models.Food
import com.example.coffwaiter.models.Restaurant
import com.example.coffwaiter.ui.cart.CartActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.NullPointerException
import java.util.*

class RestaurantActivity : AppCompatActivity(), FoodsAdapter.OnFoodsItemClickListener {

    private lateinit var binding: ActivityRestaurantBinding

    val db = Firebase.firestore

    private var foodsAdapter = FoodsAdapter(this)

    private var foods: List<Food> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.shimmerSfl.visibility = View.VISIBLE

        binding.foodListRv.layoutManager = LinearLayoutManager(this)
        binding.foodListRv.adapter = foodsAdapter

        val rid = intent.getStringExtra("rid")!!

        binding.searchIv.setOnClickListener {
            val query = binding.searchDataEt.text.toString()

            foodsAdapter.foods = foods.filter {
                    food -> food.name!!.toLowerCase(Locale.ROOT).contains(query)
            }
        }

        binding.cartIv.setOnClickListener {
            GlobalData.cartFoods = foods.filter { it.count > 0 }.toMutableList()

            val cartIntent = Intent(this, CartActivity::class.java)
            startActivity(cartIntent)
        }

        db.collection("restaurants").document(rid).get()
            .addOnSuccessListener {
                val restaurant = it.toObject<Restaurant>()

                if (restaurant?.foods == null) {
                    Toast.makeText(applicationContext, "QR-код не зарегистрирован", Toast.LENGTH_LONG).show()
                    finish()
                    return@addOnSuccessListener
                }

                foods = restaurant.foods

                if (foods.isNotEmpty()) {
                    foodsAdapter.foods = foods
                    binding.shimmerSfl.visibility = View.GONE
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Ошибка при получении данных", Toast.LENGTH_SHORT).show()
                it.printStackTrace()
            }
    }

    override fun onFoodsItemClick(food: Food) {
        supportFragmentManager.let {
            FoodInfoBottomSheetFragment(food).apply {
                show(it, tag)
            }
        }
    }

    override fun onPlusClick(food: Food) {}
    override fun onMinusClick(food: Food) {}

    override fun onResume() {
        super.onResume()
        binding.shimmerSfl.startShimmer()
        foodsAdapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerSfl.stopShimmer()
    }


}
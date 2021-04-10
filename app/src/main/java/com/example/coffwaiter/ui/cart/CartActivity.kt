package com.example.coffwaiter.ui.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffwaiter.GlobalData
import com.example.coffwaiter.databinding.ActivityCartBinding
import com.example.coffwaiter.models.Food
import com.example.coffwaiter.ui.restaurant.FoodsAdapter

class CartActivity : AppCompatActivity(), FoodsAdapter.OnFoodsItemClickListener {

    private lateinit var binding: ActivityCartBinding

    private val foodsAdapter = FoodsAdapter(this)

    private var toolsCount = 0

    private var foodsPrice = 0
    private var toolsPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodsAdapter.foods = GlobalData.cartFoods

        for (food in GlobalData.cartFoods)
            foodsPrice += food.price!! * food.count

        binding.foodsPriceTv.text = "$foodsPrice ₽"
        binding.allPriceTv.text = "${foodsPrice + toolsPrice} ₽"

        binding.backIv.setOnClickListener {
            finish()
        }

        binding.foodsListRv.layoutManager = LinearLayoutManager(this)
        binding.foodsListRv.adapter = foodsAdapter

        binding.trashIv.setOnClickListener {
            GlobalData.cartFoods.clear()
            foodsAdapter.notifyDataSetChanged()
        }

        binding.minusBtn.setOnClickListener {
            if (toolsCount > 0) toolsCount -= 1

            binding.toolsCountTv.text = toolsCount.toString()
            toolsPrice = toolsCount * 3
            binding.tableToolsPriceTv.text = "$toolsPrice ₽"
            binding.allPriceTv.text = (foodsPrice + toolsPrice).toString() + " ₽"
        }

        binding.plusBtn.setOnClickListener {
            if (toolsCount < 99) toolsCount += 1

            binding.toolsCountTv.text = toolsCount.toString()
            toolsPrice = toolsCount * 3
            binding.tableToolsPriceTv.text = "$toolsPrice ₽"
            binding.allPriceTv.text = (foodsPrice + toolsPrice).toString() + " ₽"
        }
    }

    override fun onFoodsItemClick(food: Food) {}

    override fun onPlusClick(food: Food) {
        foodsPrice += food.price!!
        binding.foodsPriceTv.text = "$foodsPrice ₽"
        binding.allPriceTv.text = (foodsPrice + toolsPrice).toString() + " ₽"
    }

    override fun onMinusClick(food: Food) {
        foodsPrice -= food.price!!
        binding.foodsPriceTv.text = "$foodsPrice ₽"
        binding.allPriceTv.text = (foodsPrice + toolsPrice).toString() + " ₽"
    }
}
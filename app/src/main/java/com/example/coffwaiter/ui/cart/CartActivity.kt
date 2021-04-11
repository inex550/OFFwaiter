package com.example.coffwaiter.ui.cart

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffwaiter.GlobalData
import com.example.coffwaiter.databinding.ActivityCartBinding
import com.example.coffwaiter.models.Food
import com.example.coffwaiter.payments.GooglePaymentUtil
import com.example.coffwaiter.ui.restaurant.FoodsAdapter
import com.example.coffwaiter.ui.wait.WaitActivity
import com.google.android.gms.wallet.*

class CartActivity : AppCompatActivity(), FoodsAdapter.OnFoodsItemClickListener {

    private lateinit var binding: ActivityCartBinding

    private val foodsAdapter = FoodsAdapter(this)

    private var toolsCount = 0

    private var foodsPrice = 0
    private var toolsPrice = 0

    private lateinit var googlePaymentsClient: PaymentsClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        googlePaymentsClient = GooglePaymentUtil.createGoogleApiClientForPay(this)

        GooglePaymentUtil.checkIsReadyGooglePay(googlePaymentsClient) { ready ->
            if (ready) {
                binding.payBtn.setOnClickListener {
                    val request =
                        GooglePaymentUtil.createPaymentDataRequest("${foodsPrice + toolsPrice}")
                    AutoResolveHelper.resolveTask(
                        googlePaymentsClient.loadPaymentData(request),
                        this,
                        LOAD_PAYMENT_DATA_REQUEST_CODE
                    )
                }
            } else {
                binding.payBtn.isActivated = false
                binding.payBtn.setOnClickListener {
                    Toast.makeText(this, "Не удаётся запустить Google Pay", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

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
            for (food in GlobalData.cartFoods)
                food.count = 0

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_CANCELED) return

            val intent = Intent(this, WaitActivity::class.java)
            startActivity(intent)
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

    companion object {
        const val LOAD_PAYMENT_DATA_REQUEST_CODE = 100
    }
}
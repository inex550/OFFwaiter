package com.example.coffwaiter.ui.restaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffwaiter.databinding.FoodsListItemBinding
import com.example.coffwaiter.models.Food

class FoodsAdapter(
    val listener: OnFoodsItemClickListener
): RecyclerView.Adapter<FoodsAdapter.ViewHolder>() {

    inner class ViewHolder(
            private val binding: FoodsListItemBinding
    ): RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(food: Food) {
            Glide.with(binding.foodImageIv.context)
                    .load(food.logo)
                    .centerInside()
                    .into(binding.foodImageIv)

            binding.nameTv.text = food.name

            binding.priceTv.text = food.price.toString()

            binding.foodCountTv.text = food.count.toString()

            binding.plusBtn.setOnClickListener {
                if (food.count < 99) {
                    food.count += 1
                    listener.onPlusClick(food)
                }

                binding.foodCountTv.text = food.count.toString()
            }

            binding.minusBtn.setOnClickListener {
                if (food.count > 0) {
                    food.count -= 1
                    listener.onMinusClick(food)
                }

                binding.foodCountTv.text = food.count.toString()
            }
        }

        override fun onClick(view: View) {
            val food = foods[adapterPosition]
            listener.onFoodsItemClick(food)
        }
    }

    interface OnFoodsItemClickListener {
        fun onFoodsItemClick(food: Food)

        fun onPlusClick(food: Food)
        fun onMinusClick(food: Food)
    }

    var foods = listOf<Food>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FoodsListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foods[position]
        holder.bind(food)
    }

    override fun getItemCount(): Int = foods.size
}
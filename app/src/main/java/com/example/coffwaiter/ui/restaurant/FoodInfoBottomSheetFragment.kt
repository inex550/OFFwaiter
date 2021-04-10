package com.example.coffwaiter.ui.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.coffwaiter.R
import com.example.coffwaiter.databinding.FragmentFoodInfoBinding
import com.example.coffwaiter.models.Food
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FoodInfoBottomSheetFragment(
    private val food: Food
): BottomSheetDialogFragment() {

    private var _binding: FragmentFoodInfoBinding? = null
    private val binding: FragmentFoodInfoBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodInfoBinding.inflate(inflater, container, false)

        this.dialog?.window?.setBackgroundDrawableResource(R.drawable.top_corners_16)

        Glide.with(requireContext())
            .load(food.logo)
            .into(binding.foodImageIv)

        binding.nameTv.text = food.name

        binding.priceTv.text = food.price.toString()
        binding.descriptionTv.text = food.description
        binding.foodCountTv.text = food.count.toString()

        binding.plusBtn.setOnClickListener {
            if (food.count < 99) food.count += 1

            binding.foodCountTv.text = food.count.toString()
        }

        binding.minusBtn.setOnClickListener {
            if (food.count > 1) food.count -= 1

            binding.foodCountTv.text = food.count.toString()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
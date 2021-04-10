package com.example.coffwaiter.models

data class Restaurant(
    val name: String? = null,
    val foods: List<Food>? = null,
    val categories: List<String>? = null
)
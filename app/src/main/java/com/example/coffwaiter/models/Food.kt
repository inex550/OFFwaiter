package com.example.coffwaiter.models

class Food(
        val name: String? = null,
        val logo: String? = null,
        val price: Int? = null,
        val category: String? = null,
        val description: String? = null,
        var count: Int = 0
)
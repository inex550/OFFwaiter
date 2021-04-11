package com.example.coffwaiter.models

class Food(
        val name: String? = null,
        val logo: String? = null,
        val price: Int? = null,
        val category: String? = null,
        val description: String? = null,
        val time: Int = 0,
        var count: Int = 0
)
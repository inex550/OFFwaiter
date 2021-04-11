package com.example.coffwaiter.models

data class Order(
    val cancel: Boolean = false,
    val processed: Boolean = false,
    val timer: Int? = null,
    val pinCode: Int? = null
)
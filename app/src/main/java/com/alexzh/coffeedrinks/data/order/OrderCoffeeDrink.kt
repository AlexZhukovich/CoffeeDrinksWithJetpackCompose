package com.alexzh.coffeedrinks.data.order

import androidx.annotation.DrawableRes

data class OrderCoffeeDrink(
    val id: Long,
    val name: String,
    @DrawableRes val imageUrl: Int,
    val ingredients: String,
    val price: Double,
    val count: Int
)

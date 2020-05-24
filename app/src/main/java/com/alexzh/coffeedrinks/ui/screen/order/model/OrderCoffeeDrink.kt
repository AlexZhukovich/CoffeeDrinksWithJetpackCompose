package com.alexzh.coffeedrinks.ui.screen.order.model

import androidx.annotation.DrawableRes

data class OrderCoffeeDrink(
    val id: Long,
    val name: String,
    @DrawableRes val imageRes: Int,
    val description: String,
    val price: Double,
    var count: Int = 0
)

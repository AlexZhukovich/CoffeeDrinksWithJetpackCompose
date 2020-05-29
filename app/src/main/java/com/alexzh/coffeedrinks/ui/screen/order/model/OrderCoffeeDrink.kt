package com.alexzh.coffeedrinks.ui.screen.order.model

import androidx.annotation.DrawableRes
import androidx.compose.MutableState
import androidx.compose.mutableStateOf

data class OrderCoffeeDrink(
    val id: Long,
    val name: String,
    @DrawableRes val imageRes: Int,
    val description: String,
    val price: Double,
    var count: MutableState<Int> = mutableStateOf(0)
)

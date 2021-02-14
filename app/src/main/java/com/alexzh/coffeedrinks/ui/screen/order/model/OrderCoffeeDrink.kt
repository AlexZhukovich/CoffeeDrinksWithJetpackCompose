package com.alexzh.coffeedrinks.ui.screen.order.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class OrderCoffeeDrink(
    val id: Long,
    val name: String,
    @DrawableRes val imageRes: Int,
    val description: String,
    val price: Double,
    // TODO: should be immutable
    var count: MutableState<Int> = mutableStateOf(0)
)

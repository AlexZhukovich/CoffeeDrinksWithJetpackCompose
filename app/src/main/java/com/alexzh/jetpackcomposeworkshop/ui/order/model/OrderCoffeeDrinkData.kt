package com.alexzh.jetpackcomposeworkshop.ui.order.model

import androidx.compose.Model
import androidx.compose.frames.ModelList

@Model
data class OrderCoffeeDrinkData(
    val orderCoffeeDrinks: ModelList<OrderCoffeeDrink>
) {
    val totalPrice: Double = orderCoffeeDrinks.asSequence()
        .filter { it.count != 0 }
        .map { it.count * it.price }
        .sum()
}
package com.alexzh.coffeedrinks.ui.screen.order.model

import androidx.compose.frames.ModelList

data class OrderCoffeeDrinkData(
    val drinks: ModelList<OrderCoffeeDrink>,
    val totalPrice: Double = drinks.asSequence()
        .filter { it.count != 0 }
        .map { it.count * it.price }
        .sum()
)

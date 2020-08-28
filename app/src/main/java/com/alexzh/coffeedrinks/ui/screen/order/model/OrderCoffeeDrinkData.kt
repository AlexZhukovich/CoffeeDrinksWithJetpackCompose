package com.alexzh.coffeedrinks.ui.screen.order.model

import androidx.compose.runtime.snapshots.SnapshotStateList

data class OrderCoffeeDrinkData(
    val drinks: SnapshotStateList<OrderCoffeeDrink>
) {
    val totalPrice: Double = drinks.asSequence()
            .filter { it.count.value != 0 }
            .map { it.count.value * it.price }
            .sum()
}

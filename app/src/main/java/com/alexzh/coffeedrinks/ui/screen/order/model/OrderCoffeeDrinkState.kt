package com.alexzh.coffeedrinks.ui.screen.order.model

import com.alexzh.coffeedrinks.data.order.OrderCoffeeDrink
import java.math.BigDecimal

data class OrderCoffeeDrinkState(
    val coffeeDrinks: List<OrderCoffeeDrink>,
    val totalPrice: BigDecimal
)

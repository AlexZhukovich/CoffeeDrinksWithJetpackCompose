package com.alexzh.jetpackcomposeworkshop.ui.order.mapper

import com.alexzh.jetpackcomposeworkshop.data.CoffeeDrink
import com.alexzh.jetpackcomposeworkshop.ui.order.model.OrderCoffeeDrink

class OrderCoffeeDrinkMapper {

    fun map(coffeeDrink: CoffeeDrink): OrderCoffeeDrink {
        return OrderCoffeeDrink(
            id = coffeeDrink.id,
            name = coffeeDrink.name,
            imageRes = coffeeDrink.imageUrl,
            description = coffeeDrink.orderDescription,
            price = coffeeDrink.price,
            count = 0
        )
    }
}
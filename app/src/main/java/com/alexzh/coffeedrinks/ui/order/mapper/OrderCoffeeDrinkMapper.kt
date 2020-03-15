package com.alexzh.coffeedrinks.ui.order.mapper

import com.alexzh.coffeedrinks.data.CoffeeDrink
import com.alexzh.coffeedrinks.ui.order.model.OrderCoffeeDrink

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
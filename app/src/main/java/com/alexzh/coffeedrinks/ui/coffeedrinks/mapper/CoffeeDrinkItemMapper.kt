package com.alexzh.coffeedrinks.ui.coffeedrinks.mapper

import com.alexzh.coffeedrinks.data.CoffeeDrink
import com.alexzh.coffeedrinks.ui.coffeedrinks.model.CoffeeDrinkItem

class CoffeeDrinkItemMapper {

    fun map(coffeeDrink: CoffeeDrink): CoffeeDrinkItem {
        return CoffeeDrinkItem(
            id = coffeeDrink.id,
            name = coffeeDrink.name,
            imageUrl = coffeeDrink.imageUrl,
            backgroundImage = coffeeDrink.backgroundImage,
            ingredients = coffeeDrink.ingredients,
            description = coffeeDrink.description,
            isFavourite = coffeeDrink.isFavourite
        )
    }
}
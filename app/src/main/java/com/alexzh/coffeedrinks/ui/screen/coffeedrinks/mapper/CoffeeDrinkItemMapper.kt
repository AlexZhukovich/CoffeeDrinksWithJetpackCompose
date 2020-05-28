package com.alexzh.coffeedrinks.ui.screen.coffeedrinks.mapper

import androidx.compose.mutableStateOf
import com.alexzh.coffeedrinks.data.CoffeeDrink
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model.CoffeeDrinkItem

class CoffeeDrinkItemMapper {

    fun map(coffeeDrink: CoffeeDrink): CoffeeDrinkItem {
        return CoffeeDrinkItem(
            id = coffeeDrink.id,
            name = coffeeDrink.name,
            imageUrl = coffeeDrink.imageUrl,
            ingredients = coffeeDrink.ingredients,
            description = coffeeDrink.description,
            isFavourite = mutableStateOf(coffeeDrink.isFavourite)
        )
    }
}

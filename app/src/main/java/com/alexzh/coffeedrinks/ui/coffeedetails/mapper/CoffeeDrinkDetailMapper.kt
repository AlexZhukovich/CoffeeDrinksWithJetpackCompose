package com.alexzh.coffeedrinks.ui.coffeedetails.mapper

import com.alexzh.coffeedrinks.data.CoffeeDrink
import com.alexzh.coffeedrinks.ui.coffeedetails.model.CoffeeDrinkDetail

class CoffeeDrinkDetailMapper {

    fun map(coffeeDrink: CoffeeDrink?) : CoffeeDrinkDetail? {
        if (coffeeDrink == null) {
            return null
        }

        return CoffeeDrinkDetail(
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
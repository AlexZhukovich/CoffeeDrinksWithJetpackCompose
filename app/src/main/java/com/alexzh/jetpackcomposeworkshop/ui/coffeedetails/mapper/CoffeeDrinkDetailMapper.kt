package com.alexzh.jetpackcomposeworkshop.ui.coffeedetails.mapper

import com.alexzh.jetpackcomposeworkshop.data.CoffeeDrink
import com.alexzh.jetpackcomposeworkshop.ui.coffeedetails.model.CoffeeDrinkDetail

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
package com.alexzh.jetpackcomposeworkshop.ui.mapper

import com.alexzh.jetpackcomposeworkshop.data.CoffeeDrink
import com.alexzh.jetpackcomposeworkshop.ui.model.CoffeeDrinkModel

class CoffeeDrinkMapper {

    fun map(coffeeDrink: CoffeeDrink): CoffeeDrinkModel {
        return CoffeeDrinkModel(
            coffeeDrink.id,
            coffeeDrink.name,
            coffeeDrink.imageUrl,
            coffeeDrink.backgroundImage,
            coffeeDrink.ingredients,
            coffeeDrink.description,
            coffeeDrink.isFavourite,
            false
        )
    }
}
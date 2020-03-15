package com.alexzh.coffeedrinks.ui.coffeedrinks.model

import androidx.compose.Model
import androidx.compose.frames.ModelList
import com.alexzh.coffeedrinks.ui.coffeedrinks.model.CoffeeDrinkItem

@Model
object CoffeeDrinksModel {
    val coffeeDrinks = ModelList<CoffeeDrinkItem>()
}
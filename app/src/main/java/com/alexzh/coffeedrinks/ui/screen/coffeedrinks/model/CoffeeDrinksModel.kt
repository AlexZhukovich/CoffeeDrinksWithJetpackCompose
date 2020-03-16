package com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model

import androidx.compose.Model
import androidx.compose.frames.ModelList

@Model
object CoffeeDrinksModel {
    val coffeeDrinks = ModelList<CoffeeDrinkItem>()
}
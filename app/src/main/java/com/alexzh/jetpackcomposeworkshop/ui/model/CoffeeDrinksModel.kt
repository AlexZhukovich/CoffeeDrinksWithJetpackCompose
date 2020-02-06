package com.alexzh.jetpackcomposeworkshop.ui.model

import androidx.compose.Model
import androidx.compose.frames.ModelList

@Model
object CoffeeDrinksModel {
    val coffeeDrinks = ModelList<CoffeeDrinkModel>()
}
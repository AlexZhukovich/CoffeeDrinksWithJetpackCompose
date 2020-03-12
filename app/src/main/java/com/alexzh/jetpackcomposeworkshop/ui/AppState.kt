package com.alexzh.jetpackcomposeworkshop.ui

import androidx.compose.Model
import com.alexzh.jetpackcomposeworkshop.ui.model.CoffeeDrinkModel

@Model
object AppState {
    var currentScreen: Screen = Screen.CoffeeDrinks
}

sealed class Screen {
    object CoffeeDrinks : Screen()
    data class CoffeeDrinkDetails(val coffeeDrink: CoffeeDrinkModel) : Screen()
}

fun navigateTo(destination: Screen) {
    AppState.currentScreen = destination
}
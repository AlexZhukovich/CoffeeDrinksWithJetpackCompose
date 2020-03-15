package com.alexzh.jetpackcomposeworkshop.ui

import androidx.compose.Model

@Model
object AppState {
    var currentScreen: Screen = Screen.CoffeeDrinks
}

sealed class Screen {
    object CoffeeDrinks : Screen()
    data class CoffeeDrinkDetails(val coffeeDrinkId: Long) : Screen()
    object OrderCoffeeDrinks : Screen()
}

fun navigateTo(destination: Screen) {
    AppState.currentScreen = destination
}
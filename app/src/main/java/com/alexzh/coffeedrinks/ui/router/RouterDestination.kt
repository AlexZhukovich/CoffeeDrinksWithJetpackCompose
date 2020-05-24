package com.alexzh.coffeedrinks.ui.router

sealed class RouterDestination {
    object CoffeeDrinks : RouterDestination()
    data class CoffeeDrinkDetails(val coffeeDrinkId: Long) : RouterDestination()
    object OrderCoffeeDrinks : RouterDestination()
}

package com.alexzh.coffeedrinks.data

interface CoffeeDrinkDataSource {

    fun getCoffeeDrinks(): List<CoffeeDrink>
}

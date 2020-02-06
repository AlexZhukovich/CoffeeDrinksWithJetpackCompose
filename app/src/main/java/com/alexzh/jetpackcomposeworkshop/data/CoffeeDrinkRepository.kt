package com.alexzh.jetpackcomposeworkshop.data

interface CoffeeDrinkRepository {

    fun getCoffeeDrinks(): List<CoffeeDrink>

    fun updateCoffeeDrink(coffeeDrink: CoffeeDrink): Boolean
}
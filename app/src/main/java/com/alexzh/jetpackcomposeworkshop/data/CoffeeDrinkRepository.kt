package com.alexzh.jetpackcomposeworkshop.data

interface CoffeeDrinkRepository {

    fun getCoffeeDrinks(): List<CoffeeDrink>

    fun getCoffeeDrink(id: Long): CoffeeDrink?

    fun updateCoffeeDrink(coffeeDrink: CoffeeDrink): Boolean
}
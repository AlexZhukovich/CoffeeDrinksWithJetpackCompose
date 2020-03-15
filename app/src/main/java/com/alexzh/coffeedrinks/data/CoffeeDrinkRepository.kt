package com.alexzh.coffeedrinks.data

import com.alexzh.coffeedrinks.data.CoffeeDrink

interface CoffeeDrinkRepository {

    fun getCoffeeDrinks(): List<CoffeeDrink>

    fun getCoffeeDrink(id: Long): CoffeeDrink?

    fun updateCoffeeDrink(coffeeDrink: CoffeeDrink): Boolean
}
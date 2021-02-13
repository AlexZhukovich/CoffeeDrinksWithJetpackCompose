package com.alexzh.coffeedrinks.data.order

interface OrderCoffeeDrinksRepository {

    suspend fun getCoffeeDrinks(): List<OrderCoffeeDrink>

    suspend fun add(coffeeDrinkId: Long): Boolean

    suspend fun remove(coffeeDrinkId: Long): Boolean
}

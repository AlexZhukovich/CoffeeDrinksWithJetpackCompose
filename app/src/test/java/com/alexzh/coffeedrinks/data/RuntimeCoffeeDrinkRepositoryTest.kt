package com.alexzh.jetpackcomposeworkshop.data

import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import org.junit.Assert.*
import org.junit.Test

class RuntimeCoffeeDrinkRepositoryTest {

    private val repository = RuntimeCoffeeDrinkRepository()

    @Test
    fun `should first call of getCoffeeDrinks() returns 10 items`() {
        val expectedItemsCount = 10

        assertEquals(expectedItemsCount, repository.getCoffeeDrinks().size)
    }

    @Test
    fun `should first call of getCoffeeDrinks() returns all non-favourite items`() {
        val expectedItemsCount = 10

        val nonFavouriteItemsCount = repository.getCoffeeDrinks().filter { !it.isFavourite }.size

        assertEquals(expectedItemsCount, nonFavouriteItemsCount)
    }

    @Test
    fun `should update favourite state of not-favourite coffee`() {
        val coffeeName = repository.getCoffeeDrinks().first().name
        val coffee = repository.getCoffeeDrinks().first { it.name == coffeeName }
        val favouriteCoffee = coffee.copy(isFavourite = true)

        repository.updateCoffeeDrink(favouriteCoffee)

        val result = repository.getCoffeeDrinks().first { it.name == coffeeName }
        assertTrue(result.isFavourite)
    }

    @Test
    fun `should update favourite state of favourite coffee`() {
        val coffeeName = repository.getCoffeeDrinks().first().name
        val coffee = repository.getCoffeeDrinks().first { it.name == coffeeName }
        val favouriteCoffee = coffee.copy(isFavourite = true)
        val nonFavouriteCoffee = coffee.copy(isFavourite = false)

        repository.updateCoffeeDrink(favouriteCoffee)
        repository.updateCoffeeDrink(nonFavouriteCoffee)

        val result = repository.getCoffeeDrinks().first { it.name == coffeeName }
        assertFalse(result.isFavourite)
    }
}
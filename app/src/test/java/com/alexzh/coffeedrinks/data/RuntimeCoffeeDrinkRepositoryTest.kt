package com.alexzh.coffeedrinks.data

import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RuntimeCoffeeDrinkRepositoryTest {
    private val repository = RuntimeCoffeeDrinkRepository

    @Test
    fun `should first call of getCoffeeDrinks() returns 10 items`() = runBlocking {
        val expectedItemsCount = 10

        assertEquals(
            expectedItemsCount,
            repository.getCoffeeDrinks().single().size
        )
    }

    @Test
    fun `should first call of getCoffeeDrinks() returns all non-favourite items`() = runBlocking {
        val expectedItemsCount = 10

        val nonFavouriteItemsCount = repository.getCoffeeDrinks()
            .single()
            .filter { !it.isFavourite }
            .size

        assertEquals(expectedItemsCount, nonFavouriteItemsCount)
    }

    @Test
    fun `should update favourite state of not-favourite coffee`() = runBlocking {
        val coffeeName = repository.getCoffeeDrinks()
            .single()
            .first().name
        val coffee = repository.getCoffeeDrinks()
            .single()
            .first { it.name == coffeeName }

        val updateCoffeeDrinkStatus = repository.updateFavouriteState(coffee.id, true).single()

        val result = repository.getCoffeeDrinks()
            .single()
            .first { it.name == coffeeName }

        assertTrue(updateCoffeeDrinkStatus)
        assertTrue(result.isFavourite)
    }

    @Test
    fun `should update favourite state of favourite coffee`() = runBlocking {
        val coffeeName = repository.getCoffeeDrinks()
            .single()
            .first().name
        val coffee = repository.getCoffeeDrinks()
            .single()
            .first { it.name == coffeeName }

        repository.updateFavouriteState(coffee.id, true)
        repository.updateFavouriteState(coffee.id, false)

        val result = repository.getCoffeeDrinks()
            .single()
            .first { it.name == coffeeName }
        assertFalse(result.isFavourite)
    }
}

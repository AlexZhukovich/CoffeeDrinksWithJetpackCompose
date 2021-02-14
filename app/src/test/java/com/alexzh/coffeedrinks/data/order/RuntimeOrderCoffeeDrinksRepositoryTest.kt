package com.alexzh.coffeedrinks.data.order

import com.alexzh.coffeedrinks.data.CoffeeDrink
import com.alexzh.coffeedrinks.data.CoffeeDrinkDataSource
import com.alexzh.coffeedrinks.generator.GenerateCoffeeDrink.generateCoffeeDrink
import com.alexzh.coffeedrinks.generator.GenerateCoffeeDrink.generateCoffeeDrinks
import com.alexzh.coffeedrinks.generator.GenerateOrderCoffeeDrink.generateOrderCoffeeDrink
import com.alexzh.coffeedrinks.generator.GenerateOrderCoffeeDrink.generateOrderCoffeeDrinks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class RuntimeOrderCoffeeDrinksRepositoryTest {
    private val coffeeDrinkDataSource = mockk<CoffeeDrinkDataSource>()
    private val orderCoffeeDrinkMapper = mockk<OrderCoffeeDrinkMapper>()

    private val repository = RuntimeOrderCoffeeDrinksRepository(
        coffeeDrinkDataSource,
        orderCoffeeDrinkMapper
    )

    @Test
    fun should_returnCoffeeDrinks_when_dataSourceReturnData() {
        val coffeeDrinks = generateCoffeeDrinks(count = 10)
        val orderCoffeeDrinks = generateOrderCoffeeDrinks(count = 10)

        stubGetCoffeeDrinks(coffeeDrinks)
        stubMapToOrderCoffeeDrink(coffeeDrinks, orderCoffeeDrinks)

        runBlocking {
            assertEquals(
                orderCoffeeDrinks,
                repository.getCoffeeDrinks()
            )
        }
    }

    @Test
    fun should_addMethod_updateCoffeeDrinksList_when_countIsLessThanMaximum() {
        val coffeeDrinks = listOf(generateCoffeeDrink())
        val orderCoffeeDrinks = listOf(generateOrderCoffeeDrink(count = 0))
        val updateOrderCoffeeDrinks = listOf(orderCoffeeDrinks.first().copy(count = 1))

        stubGetCoffeeDrinks(coffeeDrinks)
        stubMapToOrderCoffeeDrink(coffeeDrinks, orderCoffeeDrinks)

        runBlocking {
            repository.getCoffeeDrinks()
            repository.add(orderCoffeeDrinks.first().id)
            assertEquals(
                updateOrderCoffeeDrinks,
                repository.getCoffeeDrinks()
            )
        }
    }

    @Test
    fun should_addMethod_notUpdateCoffeeDrinksList_when_countIsEqualToMaximum() {
        val coffeeDrinks = listOf(generateCoffeeDrink())
        val orderCoffeeDrinks = listOf(generateOrderCoffeeDrink(count = 99))

        stubGetCoffeeDrinks(coffeeDrinks)
        stubMapToOrderCoffeeDrink(coffeeDrinks, orderCoffeeDrinks)

        runBlocking {
            repository.getCoffeeDrinks()
            repository.add(orderCoffeeDrinks.first().id)
            assertEquals(
                orderCoffeeDrinks,
                repository.getCoffeeDrinks()
            )
        }
    }

    @Test
    fun should_removeMethod_updateCoffeeDrinksList_when_countIsMoreThanMinimum() {
        val coffeeDrinks = listOf(generateCoffeeDrink())
        val orderCoffeeDrinks = listOf(generateOrderCoffeeDrink(count = 42))
        val updateOrderCoffeeDrinks = listOf(orderCoffeeDrinks.first().copy(count = 41))

        stubGetCoffeeDrinks(coffeeDrinks)
        stubMapToOrderCoffeeDrink(coffeeDrinks, orderCoffeeDrinks)

        runBlocking {
            repository.getCoffeeDrinks()
            repository.remove(orderCoffeeDrinks.first().id)
            assertEquals(
                updateOrderCoffeeDrinks,
                repository.getCoffeeDrinks()
            )
        }
    }

    @Test
    fun should_removeMethod_notUpdateCoffeeDrinksList_when_countIsEqualToMinimum() {
        val coffeeDrinks = listOf(generateCoffeeDrink())
        val orderCoffeeDrinks = listOf(generateOrderCoffeeDrink(count = 0))

        stubGetCoffeeDrinks(coffeeDrinks)
        stubMapToOrderCoffeeDrink(coffeeDrinks, orderCoffeeDrinks)

        runBlocking {
            repository.getCoffeeDrinks()
            repository.remove(orderCoffeeDrinks.first().id)
            assertEquals(
                orderCoffeeDrinks,
                repository.getCoffeeDrinks()
            )
        }
    }

    private fun stubGetCoffeeDrinks(coffeeDrinks: List<CoffeeDrink>) {
        every { coffeeDrinkDataSource.getCoffeeDrinks() } returns coffeeDrinks
    }

    private fun stubMapToOrderCoffeeDrink(
        coffeeDrinks: List<CoffeeDrink>,
        orderCoffeeDrink: List<OrderCoffeeDrink>
    ) {
        every { orderCoffeeDrinkMapper.map(coffeeDrinks) } returns orderCoffeeDrink
    }
}

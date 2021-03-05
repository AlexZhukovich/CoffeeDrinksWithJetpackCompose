package com.alexzh.coffeedrinks.data.order

import com.alexzh.coffeedrinks.data.CoffeeDrink
import com.alexzh.coffeedrinks.generator.GenerateCoffeeDrink.generateCoffeeDrink
import com.alexzh.coffeedrinks.generator.GenerateCoffeeDrink.generateCoffeeDrinks
import org.junit.Assert.assertEquals
import org.junit.Test

class OrderCoffeeDrinkMapperTest {
    private val orderCoffeeDrinkMapper = OrderCoffeeDrinkMapper()

    @Test
    fun should_mapToOrderCoffeeDrink_withDefaultCountParam_correctly() {
        val coffeeDrink = generateCoffeeDrink()
        val orderCoffeeDrink = orderCoffeeDrinkMapper.map(coffeeDrink)
        assertOrderCoffeeDrink(coffeeDrink, orderCoffeeDrink, 0)
    }

    @Test
    fun should_mapToOrderCoffeeDrink_withCustomCountParam_correctly() {
        val customCount = 42
        val coffeeDrink = generateCoffeeDrink()
        val orderCoffeeDrink = orderCoffeeDrinkMapper.map(coffeeDrink, customCount)
        assertOrderCoffeeDrink(coffeeDrink, orderCoffeeDrink, customCount)
    }

    @Test
    fun should_mapListToOrderCoffeeDrinks_withDefaultCountParam_correctly() {
        val coffeeDrinks = generateCoffeeDrinks(2)
        val orderCoffeeDrinks = orderCoffeeDrinkMapper.map(coffeeDrinks)
        assertOrderCoffeeDrinks(coffeeDrinks, orderCoffeeDrinks, 0)
    }

    @Test
    fun should_mapListToOrderCoffeeDrinks_withCustomCountParam_correctly() {
        val customCount = 42
        val coffeeDrinks = generateCoffeeDrinks(2)
        val orderCoffeeDrinks = orderCoffeeDrinkMapper.map(coffeeDrinks, customCount)
        assertOrderCoffeeDrinks(coffeeDrinks, orderCoffeeDrinks, customCount)
    }

    private fun assertOrderCoffeeDrink(
        coffeeDrink: CoffeeDrink,
        orderCoffeeDrink: OrderCoffeeDrink,
        orderCoffeeDrinkCount: Int
    ) {
        assertEquals(coffeeDrink.id, orderCoffeeDrink.id)
        assertEquals(coffeeDrink.name, orderCoffeeDrink.name)
        assertEquals(coffeeDrink.imageUrl, orderCoffeeDrink.imageUrl)
        assertEquals(coffeeDrink.ingredients, orderCoffeeDrink.ingredients)
        assertEquals(coffeeDrink.price, orderCoffeeDrink.price, 0.00001)
        assertEquals(orderCoffeeDrink.count, orderCoffeeDrinkCount)
    }

    private fun assertOrderCoffeeDrinks(
        coffeeDrinks: List<CoffeeDrink>,
        orderCoffeeDrinks: List<OrderCoffeeDrink>,
        orderCoffeeDrinkCount: Int
    ) {
        assertEquals(coffeeDrinks.size, orderCoffeeDrinks.size)
        for (index in coffeeDrinks.indices) {
            assertOrderCoffeeDrink(
                coffeeDrinks[index],
                orderCoffeeDrinks[index],
                orderCoffeeDrinkCount
            )
        }
    }
}

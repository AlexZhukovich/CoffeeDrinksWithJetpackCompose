package com.alexzh.coffeedrinks.data.order

import com.alexzh.coffeedrinks.data.CoffeeDrinkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RuntimeOrderCoffeeDrinksRepository(
    private val coffeeDrinkDataSource: CoffeeDrinkDataSource,
    private val orderCoffeeDrinkMapper: OrderCoffeeDrinkMapper
) : OrderCoffeeDrinksRepository {
    companion object {
        const val MIN_VALUE = 0
        const val MAX_VALUE = 99
    }

    private val coffeeDrinks = mutableListOf<OrderCoffeeDrink>()

    override suspend fun getCoffeeDrinks(): Flow<List<OrderCoffeeDrink>> {
        return flow {
            if (coffeeDrinks.isEmpty()) {
                coffeeDrinks.addAll(
                    orderCoffeeDrinkMapper.map(coffeeDrinkDataSource.getCoffeeDrinks())
                )
            }
            emit(coffeeDrinks)
        }
    }

    override suspend fun add(coffeeDrinkId: Long): Flow<Boolean> {
        return flow {
            val index = coffeeDrinks.indexOfFirst { it.id == coffeeDrinkId }
            val result = if (index > -1) {
                val coffeeDrink = coffeeDrinks[index]
                val newValue =
                    if (coffeeDrink.count == MAX_VALUE) MAX_VALUE else coffeeDrink.count + 1
                coffeeDrinks[index] = coffeeDrink.copy(count = newValue)
                true
            } else {
                false
            }
            emit(result)
        }
    }

    override suspend fun remove(coffeeDrinkId: Long): Flow<Boolean> {
        return flow {
            val index = coffeeDrinks.indexOfFirst { it.id == coffeeDrinkId }
            val result = if (index > -1) {
                val coffeeDrink = coffeeDrinks[index]
                val newValue =
                    if (coffeeDrink.count == MIN_VALUE) MIN_VALUE else coffeeDrink.count - 1
                coffeeDrinks[index] = coffeeDrink.copy(count = newValue)
                true
            } else {
                false
            }
            emit(result)
        }
    }
}

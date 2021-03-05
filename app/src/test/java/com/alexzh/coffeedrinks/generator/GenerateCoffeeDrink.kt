package com.alexzh.coffeedrinks.generator

import com.alexzh.coffeedrinks.data.CoffeeDrink
import com.alexzh.coffeedrinks.generator.RandomData.randomBoolean
import com.alexzh.coffeedrinks.generator.RandomData.randomDouble
import com.alexzh.coffeedrinks.generator.RandomData.randomInt
import com.alexzh.coffeedrinks.generator.RandomData.randomLong
import com.alexzh.coffeedrinks.generator.RandomData.randomString

object GenerateCoffeeDrink {

    fun generateCoffeeDrink(
        id: Long = randomLong(),
        name: String = randomString(),
        imageUrl: Int = randomInt(),
        description: String = randomString(),
        ingredients: String = randomString(),
        orderDescription: String = randomString(),
        price: Double = randomDouble(),
        isFavourite: Boolean = randomBoolean()
    ) = CoffeeDrink(
        id = id,
        name = name,
        imageUrl = imageUrl,
        description = description,
        ingredients = ingredients,
        orderDescription = orderDescription,
        price = price,
        isFavourite = isFavourite
    )

    fun generateCoffeeDrinks(
        count: Int = 10
    ): List<CoffeeDrink> {
        val coffeeDrinks = mutableListOf<CoffeeDrink>()
        repeat(count) {
            coffeeDrinks.add(generateCoffeeDrink())
        }
        return coffeeDrinks
    }
}

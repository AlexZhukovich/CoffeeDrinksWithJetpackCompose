package com.alexzh.coffeedrinks.generator

import java.util.UUID
import kotlin.random.Random

object RandomData {

    fun randomString() = UUID.randomUUID().toString()

    fun randomLong() = Random.nextLong()

    fun randomInt(until: Int = 1000) = Random.nextInt(until)

    fun randomDouble() = Random.nextDouble()

    fun randomBoolean() = Random.nextBoolean()
}

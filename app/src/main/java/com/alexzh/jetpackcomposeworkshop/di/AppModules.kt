package com.alexzh.jetpackcomposeworkshop.di

import com.alexzh.jetpackcomposeworkshop.data.CoffeeDrinkRepository
import com.alexzh.jetpackcomposeworkshop.data.RuntimeCoffeeDrinkRepository
import com.alexzh.jetpackcomposeworkshop.ui.coffeedetails.mapper.CoffeeDrinkDetailMapper
import com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks.mapper.CoffeeDrinkItemMapper
import com.alexzh.jetpackcomposeworkshop.ui.order.mapper.OrderCoffeeDrinkMapper
import org.koin.dsl.module

val dataModule = module {
    single<CoffeeDrinkRepository> { RuntimeCoffeeDrinkRepository }
}

val mapperModule = module {
    factory { CoffeeDrinkItemMapper() }
    factory { CoffeeDrinkDetailMapper() }
    factory { OrderCoffeeDrinkMapper() }
}
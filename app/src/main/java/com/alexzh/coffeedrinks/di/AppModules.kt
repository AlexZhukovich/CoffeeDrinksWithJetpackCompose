package com.alexzh.coffeedrinks.di

import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.coffeedetails.mapper.CoffeeDrinkDetailMapper
import com.alexzh.coffeedrinks.ui.coffeedrinks.mapper.CoffeeDrinkItemMapper
import com.alexzh.coffeedrinks.ui.order.mapper.OrderCoffeeDrinkMapper
import org.koin.dsl.module

val dataModule = module {
    single<CoffeeDrinkRepository> { RuntimeCoffeeDrinkRepository }
}

val mapperModule = module {
    factory { CoffeeDrinkItemMapper() }
    factory { CoffeeDrinkDetailMapper() }
    factory { OrderCoffeeDrinkMapper() }
}
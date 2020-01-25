package com.alexzh.jetpackcomposeworkshop.di

import com.alexzh.jetpackcomposeworkshop.data.CoffeeDrinkRepository
import com.alexzh.jetpackcomposeworkshop.data.RuntimeCoffeeDrinkRepository
import org.koin.dsl.module

val dataModule = module {
    single<CoffeeDrinkRepository> { RuntimeCoffeeDrinkRepository() }
}
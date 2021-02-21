package com.alexzh.coffeedrinks.di

import com.alexzh.coffeedrinks.data.CoffeeDrinkDataSource
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.data.DummyCoffeeDrinksDataSource
import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import com.alexzh.coffeedrinks.data.order.OrderCoffeeDrinksRepository
import com.alexzh.coffeedrinks.data.order.RuntimeOrderCoffeeDrinksRepository
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.CoffeeDrinkDetailsViewModel
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.mapper.CoffeeDrinkDetailMapper
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.CoffeeDrinksViewModel
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.mapper.CoffeeDrinkItemMapper
import com.alexzh.coffeedrinks.ui.screen.order.OrderCoffeeDrinkViewModel
import com.alexzh.coffeedrinks.ui.screen.order.mapper.OrderCoffeeDrinkMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    factory<CoffeeDrinkDataSource> { DummyCoffeeDrinksDataSource() }
    single<CoffeeDrinkRepository> { RuntimeCoffeeDrinkRepository }
    single<OrderCoffeeDrinksRepository> {
        RuntimeOrderCoffeeDrinksRepository(
            coffeeDrinkDataSource = get(),
            orderCoffeeDrinkMapper = get()
        )
    }
}

val mapperModule = module {
    factory { CoffeeDrinkItemMapper() }
    factory { CoffeeDrinkDetailMapper() }
    factory { OrderCoffeeDrinkMapper() }

    factory { com.alexzh.coffeedrinks.data.order.OrderCoffeeDrinkMapper() }
}

val viewModelModule = module {
    viewModel {
        OrderCoffeeDrinkViewModel(
            repository = get()
        )
    }
    viewModel {
        CoffeeDrinksViewModel(
            repository = get(),
            mapper = get()
        )
    }
    viewModel {
        CoffeeDrinkDetailsViewModel(
            repository = get(),
            mapper = get()
        )
    }
}

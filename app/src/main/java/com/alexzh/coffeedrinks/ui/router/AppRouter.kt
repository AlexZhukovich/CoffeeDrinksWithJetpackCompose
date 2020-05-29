package com.alexzh.coffeedrinks.ui.router

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AppRouter : Router {

    private val current = MutableStateFlow<RouterDestination>(RouterDestination.CoffeeDrinks)

    override val currentDestination: RouterDestination
        get() = current.value

    override val observeCurrentDestination: Flow<RouterDestination>
        get() = current

    override fun navigateTo(destination: RouterDestination) {
        current.value = destination
    }
}

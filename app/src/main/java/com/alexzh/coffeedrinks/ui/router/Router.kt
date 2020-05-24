package com.alexzh.coffeedrinks.ui.router

import kotlinx.coroutines.flow.Flow

interface Router {

    val currentDestination: RouterDestination

    val observeCurrentDestination: Flow<RouterDestination>

    fun navigateTo(destination: RouterDestination)
}

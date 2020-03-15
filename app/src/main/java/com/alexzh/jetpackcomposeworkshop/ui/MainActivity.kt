package com.alexzh.jetpackcomposeworkshop.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import com.alexzh.jetpackcomposeworkshop.data.CoffeeDrinkRepository
import com.alexzh.jetpackcomposeworkshop.ui.coffeedetails.CoffeeDrinkDetailsScreen
import com.alexzh.jetpackcomposeworkshop.ui.coffeedetails.mapper.CoffeeDrinkDetailMapper
import com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks.CoffeeDrinksScreen
import com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks.mapper.CoffeeDrinkItemMapper
import com.alexzh.jetpackcomposeworkshop.ui.order.OrderCoffeeDrinkScreen
import com.alexzh.jetpackcomposeworkshop.ui.order.mapper.OrderCoffeeDrinkMapper
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val repository: CoffeeDrinkRepository by inject()
    private val coffeeDrinkItemMapper: CoffeeDrinkItemMapper by inject()
    private val coffeeDrinkDetailMapper: CoffeeDrinkDetailMapper by inject()
    private val orderCoffeeDrinkMapper: OrderCoffeeDrinkMapper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppContent()
            }
        }
    }

    @Composable
    fun AppContent() {
        Crossfade(current = AppState.currentScreen) { screen ->
            when (screen) {
                is Screen.CoffeeDrinks -> CoffeeDrinksScreen(repository, coffeeDrinkItemMapper)
                is Screen.CoffeeDrinkDetails -> CoffeeDrinkDetailsScreen(repository, coffeeDrinkDetailMapper, screen.coffeeDrinkId)
                is Screen.OrderCoffeeDrinks -> OrderCoffeeDrinkScreen(repository, orderCoffeeDrinkMapper)
            }
        }
    }
}

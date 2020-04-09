package com.alexzh.coffeedrinks.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.CoffeeDrinkDetailsScreen
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.mapper.CoffeeDrinkDetailMapper
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.CoffeeDrinksScreen
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.mapper.CoffeeDrinkItemMapper
import com.alexzh.coffeedrinks.ui.screen.order.OrderCoffeeDrinkScreen
import com.alexzh.coffeedrinks.ui.screen.order.mapper.OrderCoffeeDrinkMapper
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val repository: CoffeeDrinkRepository by inject()
    private val coffeeDrinkItemMapper: CoffeeDrinkItemMapper by inject()
    private val coffeeDrinkDetailMapper: CoffeeDrinkDetailMapper by inject()
    private val orderCoffeeDrinkMapper: OrderCoffeeDrinkMapper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }

    @Composable
    fun AppContent() {
        MaterialTheme(colors = lightThemeColors, typography = appTypography) {
            Crossfade(current = AppState.currentScreen) { screen ->
                when (screen) {
                    is Screen.CoffeeDrinks -> CoffeeDrinksScreen(repository, coffeeDrinkItemMapper)
                    is Screen.CoffeeDrinkDetails -> CoffeeDrinkDetailsScreen(repository, coffeeDrinkDetailMapper, screen.coffeeDrinkId)
                    is Screen.OrderCoffeeDrinks -> OrderCoffeeDrinkScreen(repository, orderCoffeeDrinkMapper)
                }
            }
        }
    }
}

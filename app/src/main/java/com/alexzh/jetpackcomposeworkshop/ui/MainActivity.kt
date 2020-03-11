package com.alexzh.jetpackcomposeworkshop.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import com.alexzh.jetpackcomposeworkshop.ui.coffeedetails.CoffeeDrinkDetailsScreen
import com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks.CoffeeDrinksScreen

class MainActivity : AppCompatActivity() {

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
                is Screen.CoffeeDrinks -> CoffeeDrinksScreen()
                is Screen.CoffeeDrinkDetails -> CoffeeDrinkDetailsScreen(coffeeDrink = screen.coffeeDrink)
            }
        }
    }
}

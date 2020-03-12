package com.alexzh.jetpackcomposeworkshop.ui

import android.os.Bundle
import android.transition.Scene
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.setContent
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.graphics.painter.Painter
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.material.AppBarIcon
import androidx.ui.material.BottomAppBar
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.res.imageResource
import com.alexzh.jetpackcomposeworkshop.R
import com.alexzh.jetpackcomposeworkshop.ui.coffeedetails.CoffeeDrinkDetailsScreen
import com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks.CoffeeDrinksScreen
import com.alexzh.jetpackcomposeworkshop.ui.order.OrderCoffeeDrinkScreen

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
                is Screen.OrderCoffeeDrinks -> OrderCoffeeDrinkScreen()
            }
        }
    }
}

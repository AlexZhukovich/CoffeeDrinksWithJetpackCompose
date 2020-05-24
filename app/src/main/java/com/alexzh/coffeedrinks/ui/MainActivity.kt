package com.alexzh.coffeedrinks.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.ui.animation.Crossfade
import androidx.ui.core.setContent
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.material.MaterialTheme
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.router.Router
import com.alexzh.coffeedrinks.ui.router.RouterDestination
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
    private val router: Router by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }

    @Composable
    fun AppContent() {
        val colorPalette = if (isSystemInDarkTheme()) {
            darkThemeColors
        } else {
            lightThemeColors
        }

        MaterialTheme(colors = colorPalette, typography = appTypography) {
            val currentScreen = router
                .observeCurrentDestination
                .collectAsState(initial = router.currentDestination)
                .value

            Crossfade(current = currentScreen) { screen ->
                when (screen) {
                    is RouterDestination.CoffeeDrinks -> CoffeeDrinksScreen(
                        router,
                        repository,
                        coffeeDrinkItemMapper
                    )
                    is RouterDestination.CoffeeDrinkDetails -> CoffeeDrinkDetailsScreen(
                        router,
                        repository,
                        coffeeDrinkDetailMapper,
                        screen.coffeeDrinkId
                    )
                    is RouterDestination.OrderCoffeeDrinks -> OrderCoffeeDrinkScreen(
                        router,
                        repository,
                        orderCoffeeDrinkMapper
                    )
                }
            }
        }
    }
}

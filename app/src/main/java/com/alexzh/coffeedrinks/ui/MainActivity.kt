package com.alexzh.coffeedrinks.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.setContent
import com.alexzh.coffeedrinks.ui.router.Router
import com.alexzh.coffeedrinks.ui.router.RouterDestination
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.CoffeeDrinkDetailsScreen
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.CoffeeDrinkDetailsViewModel
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.CoffeeDrinksScreen
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.CoffeeDrinksViewModel
import com.alexzh.coffeedrinks.ui.screen.order.OrderCoffeeDrinkScreen
import com.alexzh.coffeedrinks.ui.screen.order.OrderCoffeeDrinkViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val router: Router by inject()
    private val coffeeDrinksViewModel: CoffeeDrinksViewModel by viewModel()
    private val coffeeDrinkDetailsViewModel: CoffeeDrinkDetailsViewModel by viewModel()
    private val orderViewModel: OrderCoffeeDrinkViewModel by viewModel()

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
                        coffeeDrinksViewModel
                    )
                    is RouterDestination.CoffeeDrinkDetails -> CoffeeDrinkDetailsScreen(
                        router,
                        coffeeDrinkDetailsViewModel,
                        screen.coffeeDrinkId
                    )
                    is RouterDestination.OrderCoffeeDrinks -> OrderCoffeeDrinkScreen(
                        router,
                        orderViewModel
                    )
                }
            }
        }
    }
}

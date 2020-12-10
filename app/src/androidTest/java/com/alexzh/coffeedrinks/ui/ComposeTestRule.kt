package com.alexzh.coffeedrinks.ui

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.router.Router
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.CoffeeDrinksScreen
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.mapper.CoffeeDrinkItemMapper

fun ComposeTestRule.launchCoffeeDrinksScreen(
    router: Router,
    repository: CoffeeDrinkRepository,
    mapper: CoffeeDrinkItemMapper
) {
    setContent {
        CoffeeDrinksScreen(router, repository, mapper)
    }
}

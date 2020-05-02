package com.alexzh.coffeedrinks.ui

import androidx.ui.test.ComposeTestRule
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.CoffeeDrinksScreen
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.mapper.CoffeeDrinkItemMapper

fun ComposeTestRule.launchCoffeeDrinksScreen(
    repository: CoffeeDrinkRepository,
    mapper: CoffeeDrinkItemMapper
) {
    setContent {
        CoffeeDrinksScreen(repository, mapper)
    }
}

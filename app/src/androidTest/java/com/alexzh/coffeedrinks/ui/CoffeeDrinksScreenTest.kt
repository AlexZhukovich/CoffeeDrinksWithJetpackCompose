package com.alexzh.coffeedrinks.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithSubstring
import androidx.compose.ui.test.onNodeWithText
import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.router.AppRouter
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.mapper.CoffeeDrinkItemMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CoffeeDrinksScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val repository = RuntimeCoffeeDrinkRepository
    private val mapper = CoffeeDrinkItemMapper()
    private val router = AppRouter()

    @Before
    fun setUp() {
        composeTestRule.launchCoffeeDrinksScreen(router, repository, mapper)
    }

    @Test
    fun shouldLaunchApp() {
        composeTestRule
                .onNodeWithText("Coffee Drinks")
                .assertIsDisplayed()
    }

    @Test
    fun shouldLoadAmericano() {
        composeTestRule
                .onNodeWithSubstring("Americano")
                .assertIsDisplayed()
    }
}

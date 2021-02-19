package com.alexzh.coffeedrinks.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithSubstring
import androidx.compose.ui.test.onNodeWithText
import com.alexzh.coffeedrinks.ui.router.AppRouter
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.CoffeeDrinksScreen
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.CoffeeDrinksViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.inject
import org.koin.test.KoinTest

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CoffeeDrinksScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val router = AppRouter()
    private val coffeeDrinksViewModel by inject<CoffeeDrinksViewModel>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            CoffeeDrinksScreen(router, coffeeDrinksViewModel)
        }
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

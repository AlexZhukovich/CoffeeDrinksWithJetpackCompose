package com.alexzh.coffeedrinks.ui

import androidx.ui.test.assertIsDisplayed
import androidx.ui.test.createComposeRule
import androidx.ui.test.onNodeWithSubstring
import androidx.ui.test.onNodeWithText
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
        onNodeWithText("Coffee Drinks").assertIsDisplayed()
    }

    @Test
    fun shouldLoadAmericano() {
        onNodeWithSubstring("Americano").assertIsDisplayed()
    }
}

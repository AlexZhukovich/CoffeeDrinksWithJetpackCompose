package com.alexzh.coffeedrinks.ui

import androidx.ui.test.assertIsDisplayed
import androidx.ui.test.createComposeRule
import androidx.ui.test.findBySubstring
import androidx.ui.test.findByText
import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.mapper.CoffeeDrinkItemMapper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CoffeeDrinksScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val repository = RuntimeCoffeeDrinkRepository
    private val mapper = CoffeeDrinkItemMapper()

    @Before
    fun setUp() {
        composeTestRule.launchCoffeeDrinksScreen(repository, mapper)
    }

    @Test
    fun shouldLaunchApp() {
        findByText("Coffee Drinks").assertIsDisplayed()
    }

    @Test
    fun shouldLoadAmericano() {
        findBySubstring("Americano").assertIsDisplayed()
    }
}

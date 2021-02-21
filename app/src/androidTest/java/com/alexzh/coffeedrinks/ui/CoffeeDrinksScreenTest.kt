package com.alexzh.coffeedrinks.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithSubstring
import androidx.compose.ui.test.onNodeWithText
import androidx.fragment.app.testing.launchFragmentInContainer
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.CoffeeDrinksFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.test.KoinTest

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CoffeeDrinksScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        launchFragmentInContainer<CoffeeDrinksFragment>(
            themeResId = R.style.AppTheme
        )
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

package com.alexzh.coffeedrinks.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.filters.MediumTest
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FavouriteTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @MediumTest
    @Test
    fun animationShouldBeRenderedCorrectly() {
        composeTestRule.apply {
            setContent {
                mainClock.autoAdvance = false

                val state = remember { mutableStateOf(false) }

                LaunchedEffect("LaunchAnimation") {
                    state.value = true
                }
                Favourite(
                    state = state,
                    modifier = Modifier.size(150.dp),
                    onValueChanged = { },
                    tint = Color.Red
                )
            }

            compareScreenshot(composeTestRule, "favourite-animation-state-0")

            mainClock.advanceTimeBy(100)
            compareScreenshot(composeTestRule, "favourite-animation-state-1")

            mainClock.advanceTimeBy(150)
            compareScreenshot(composeTestRule, "favourite-animation-state-2")
        }
    }
}

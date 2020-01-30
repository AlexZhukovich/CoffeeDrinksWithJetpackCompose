package com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks

import androidx.compose.Composable
import androidx.compose.frames.ModelList
import androidx.ui.core.Opacity
import androidx.ui.core.dp
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.Spacing
import androidx.ui.material.Divider
import androidx.ui.material.ripple.Ripple
import com.alexzh.jetpackcomposeworkshop.ui.model.CoffeeDrinkModel

@Composable
fun CoffeeDrinkList(
    coffeeDrinks: ModelList<CoffeeDrinkModel>,
    onCoffeeDrinkClicked: (CoffeeDrinkModel) -> Unit,
    onFavouriteStateChanged: (CoffeeDrinkModel) -> Unit
) {
    VerticalScroller {
        Column {
            for (coffee in coffeeDrinks) {
                Ripple(bounded = true) {
                    Clickable(onClick = { onCoffeeDrinkClicked(coffee) }) {
                        Container {
                            Column {
                                CoffeeDrinkCard(
                                    coffeeDrink = coffee,
                                    onFavouriteStateChanged = { onFavouriteStateChanged(it) }
                                )
                                CoffeeDrinkDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CoffeeDrinkDivider() {
    Opacity(0.08f) {
        Divider(modifier = Spacing(left = 88.dp), color = Color.Black)
    }
}
package com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks

import androidx.compose.Composable
import androidx.compose.frames.ModelList
import androidx.ui.core.Opacity
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Clickable
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.LayoutPadding
import androidx.ui.material.Divider
import androidx.ui.material.ripple.Ripple
import androidx.ui.unit.dp
import com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks.model.CoffeeDrinkItem

@Composable
fun CoffeeDrinkList(
    status: Status,
    coffeeDrinks: ModelList<CoffeeDrinkItem>,
    onCoffeeDrinkClicked: (CoffeeDrinkItem) -> Unit,
    onFavouriteStateChanged: (CoffeeDrinkItem) -> Unit
) {
    AdapterList(
        data = coffeeDrinks
    ) { coffeeDrink ->
        Ripple(bounded = true) {
            Clickable(onClick = { onCoffeeDrinkClicked(coffeeDrink) }) {
                Column {
                    if (status.isExtendedListItem) {
                        Container(modifier = LayoutPadding(8.dp)) {
                            CoffeeDrinkGridCard(
                                coffeeDrink = coffeeDrink,
                                onFavouriteStateChanged = {
                                    onFavouriteStateChanged(
                                        it
                                    )
                                }
                            )
                        }
                    } else {
                        CoffeeDrinkListCard(
                            coffeeDrink = coffeeDrink,
                            onFavouriteStateChanged = { onFavouriteStateChanged(it) }
                        )
                        CoffeeDrinkDivider()
                    }
                }
            }
        }
    }
}

@Composable
private fun CoffeeDrinkDivider() {
    Opacity(0.08f) {
        Divider(modifier = LayoutPadding(start = 88.dp), color = Color.Black)
    }
}
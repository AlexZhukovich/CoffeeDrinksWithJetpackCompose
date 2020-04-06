package com.alexzh.coffeedrinks.ui.screen.coffeedrinks

import androidx.compose.Composable
import androidx.compose.frames.ModelList
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Box
import androidx.ui.foundation.Clickable
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.padding
import androidx.ui.material.Divider
import androidx.ui.material.ripple.ripple
import androidx.ui.unit.dp
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model.CoffeeDrinkItem

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
        Clickable(
                onClick = { onCoffeeDrinkClicked(coffeeDrink) },
                modifier = Modifier.ripple(bounded = true)) {
            Column {
                if (status.isExtendedListItem) {
                    Box(modifier = Modifier.padding(8.dp)) {
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

@Composable
private fun CoffeeDrinkDivider() {
    Divider(modifier = Modifier.padding(start = 88.dp), color = Color.LightGray)
}
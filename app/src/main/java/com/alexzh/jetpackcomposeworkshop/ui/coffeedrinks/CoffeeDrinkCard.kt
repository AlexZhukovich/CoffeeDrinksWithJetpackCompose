package com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.DrawImage
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.FlexRow
import androidx.ui.layout.Spacing
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextOverflow
import com.alexzh.jetpackcomposeworkshop.R
import com.alexzh.jetpackcomposeworkshop.ui.components.Favourite
import com.alexzh.jetpackcomposeworkshop.ui.model.CoffeeDrinkModel

@Composable
fun CoffeeDrinkCard(coffeeDrink: CoffeeDrinkModel, onFavouriteStateChanged: (CoffeeDrinkModel) -> Unit) {
    MaterialTheme {
        Column {
            FlexRow {
                inflexible {
                    CoffeeDrinkLogo(id = coffeeDrink.imageUrl)
                }
                expanded(flex = 1f) {
                    Container(alignment = Alignment.TopLeft) {
                        Column {
                            CoffeeDrinkTitle(title = coffeeDrink.name)
                            CoffeeDrinkIngredient(ingredients = coffeeDrink.ingredients)
                        }
                    }
                }
                inflexible {
                    CoffeeDrinkFavouriteIcon(
                        favouriteState = coffeeDrink.isFavourite,
                        onValueChanged = { onFavouriteStateChanged(coffeeDrink) }
                    )
                }
            }
            CoffeeDrinkDescription(description = coffeeDrink.description, show = coffeeDrink.isExtended)
        }
    }
}

@Composable
fun CoffeeDrinkLogo(@DrawableRes id: Int) {
    Container(width = 80.dp, height = 80.dp, alignment = Alignment.Center) {
        DrawImage(image = +imageResource(id))
    }
}

@Composable
fun CoffeeDrinkFavouriteIcon(
    favouriteState: Boolean,
    onValueChanged: (Boolean) -> Unit
) {
    Ripple(
        bounded = false,
        radius = 24.dp
    ) {
        Favourite(
            state = favouriteState,
            onValueChanged = onValueChanged,
            favouriteVectorId = R.drawable.ic_baseline_favorite_24,
            nonFavouriteVectorId = R.drawable.ic_baseline_favorite_border_24,
            tint = Color.Red
        )
    }
}

@Composable
fun CoffeeDrinkTitle(title: String) {
    Text(
        text = title,
        modifier = Spacing(
            left = 8.dp,
            right = 8.dp,
            top = 8.dp
        ),
        maxLines = 1
    )
}

@Composable
fun CoffeeDrinkIngredient(ingredients: String) {
    Text(
        text = ingredients,
        modifier = Spacing(left = 8.dp, right = 8.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun CoffeeDrinkDescription(description: String, show: Boolean) {
    if (show) {
        Text(
            text = description,
            modifier = Spacing(top = 4.dp, left = 88.dp, right = 8.dp, bottom = 8.dp)
        )
    }
}
package com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.foundation.DrawImage
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.alexzh.jetpackcomposeworkshop.R
import com.alexzh.jetpackcomposeworkshop.data.RuntimeCoffeeDrinkRepository
import com.alexzh.jetpackcomposeworkshop.ui.components.Favourite
import com.alexzh.jetpackcomposeworkshop.ui.mapper.CoffeeDrinkMapper
import com.alexzh.jetpackcomposeworkshop.ui.model.CoffeeDrinkModel

@Preview
@Composable
fun previewCard() {
    CoffeeDrinkCard(
        coffeeDrink = CoffeeDrinkMapper().map(RuntimeCoffeeDrinkRepository().getCoffeeDrinks()[2]),
        onFavouriteStateChanged = {}
    )
}

@Composable
fun CoffeeDrinkCard(
    coffeeDrink: CoffeeDrinkModel,
    onFavouriteStateChanged: (CoffeeDrinkModel) -> Unit
) {
    MaterialTheme {
        Column {
            Row {
                CoffeeDrinkLogo(id = coffeeDrink.imageUrl)
                Container(
                    alignment = Alignment.TopLeft,
                    modifier = LayoutFlexible(1f)
                ) {
                    Column {
                        CoffeeDrinkTitle(title = coffeeDrink.name)
                        CoffeeDrinkIngredient(ingredients = coffeeDrink.ingredients)
                    }
                }
                CoffeeDrinkFavouriteIcon(
                    favouriteState = coffeeDrink.isFavourite,
                    onValueChanged = { onFavouriteStateChanged(coffeeDrink) }
                )
            }
        }
        CoffeeDrinkDescription(
            description = coffeeDrink.description,
            show = coffeeDrink.isExtended
        )
    }
}

@Composable
fun CoffeeDrinkLogo(@DrawableRes id: Int) {
    Container(modifier = LayoutSize(80.dp), alignment = Alignment.Center) {
        DrawImage(image = imageResource(id))
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
        modifier = LayoutPadding(
            left = 8.dp,
            right = 8.dp,
            top = 8.dp,
            bottom = 8.dp
        ),
        style = TextStyle(fontSize = 24.sp),
        maxLines = 1
    )
}

@Composable
fun CoffeeDrinkIngredient(ingredients: String) {
    Text(
        text = ingredients,
        modifier = LayoutPadding(left = 8.dp, right = 8.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun CoffeeDrinkDescription(description: String, show: Boolean) {
    if (show) {
        Text(
            text = description,
            modifier = LayoutPadding(left = 88.dp, right = 8.dp, bottom = 8.dp)
        )
    }
}
package com.alexzh.coffeedrinks.ui.screen.coffeedrinks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexzh.coffeedrinks.data.DummyCoffeeDrinksDataSource
import com.alexzh.coffeedrinks.ui.appTypography
import com.alexzh.coffeedrinks.ui.component.Favourite
import com.alexzh.coffeedrinks.ui.lightThemeColors
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.mapper.CoffeeDrinkItemMapper
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model.CoffeeDrinkItem

@Preview
@Composable
fun Preview_DetailedListItem() {
    MaterialTheme(colors = lightThemeColors, typography = appTypography) {
        val mapper = CoffeeDrinkItemMapper()
        val coffeeDrink = mapper.map(
            DummyCoffeeDrinksDataSource().getCoffeeDrinks().first()
        )
        CoffeeDrinkDetailedItem(coffeeDrink) {}
    }
}

@Composable
fun CoffeeDrinkDetailedItem(
    coffeeDrink: CoffeeDrinkItem,
    onFavouriteStateChanged: (CoffeeDrinkItem) -> Unit
) {
    val favouriteState = remember { mutableStateOf(coffeeDrink.isFavourite) }

    Card {
        Column {
            Box(
                modifier = Modifier.height(200.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .padding(8.dp)
            ) {
                Favourite(
                    state = favouriteState,
                    modifier = Modifier.align(Alignment.TopEnd).alpha(0.78f),
                    onValueChanged = {
                        onFavouriteStateChanged(coffeeDrink)
                        favouriteState.value = !favouriteState.value
                    },
                    tint = MaterialTheme.colors.onPrimary
                )
                Image(
                    painter = BitmapPainter(ImageBitmap.imageResource(id = coffeeDrink.imageUrl)),
                    modifier = Modifier.align(Alignment.Center)
                        .size(100.dp),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.align(Alignment.BottomStart),
                    text = coffeeDrink.name,
                    style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.onPrimary
                    )
                )
            }
            Text(
                text = coffeeDrink.description,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(8.dp)
                    .alpha(0.54f)
            )
        }
    }
}

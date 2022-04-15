package com.alexzh.coffeedrinks.ui.screen.coffeedrinks

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexzh.coffeedrinks.data.DummyCoffeeDrinksDataSource
import com.alexzh.coffeedrinks.ui.appTypography
import com.alexzh.coffeedrinks.ui.component.AppDivider
import com.alexzh.coffeedrinks.ui.component.Favourite
import com.alexzh.coffeedrinks.ui.lightThemeColors
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.mapper.CoffeeDrinkItemMapper
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model.CoffeeDrinkItem

private val COFFEE_DRINK_IMAGE_SIZE = 72.dp

@Preview
@Composable
fun PreviewListItem() {
    val mapper = CoffeeDrinkItemMapper()
    val coffeeDrink = mapper.map(
        DummyCoffeeDrinksDataSource().getCoffeeDrinks()[5]
    )
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MaterialTheme(colors = lightThemeColors, typography = appTypography) {
            CoffeeDrinkListItem(
                coffeeDrink = coffeeDrink,
                onFavouriteStateChanged = {}
            )
        }
    }
}

@Composable
fun CoffeeDrinkList(
    coffeeDrink: CoffeeDrinkItem,
    onFavouriteStateChanged: (CoffeeDrinkItem) -> Unit
) {
    Column {
        CoffeeDrinkListItem(
            coffeeDrink = coffeeDrink,
            onFavouriteStateChanged = { onFavouriteStateChanged(it) }
        )
        AppDivider(PaddingValues(start = COFFEE_DRINK_IMAGE_SIZE))
    }
}

@Composable
fun CoffeeDrinkListItem(
    coffeeDrink: CoffeeDrinkItem,
    onFavouriteStateChanged: (CoffeeDrinkItem) -> Unit
) {
    Row {
        CoffeeDrinkLogo(id = coffeeDrink.imageUrl)
        Box(
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
        ) {
            Column {
                CoffeeDrinkTitle(title = coffeeDrink.name)
                CoffeeDrinkIngredient(ingredients = coffeeDrink.ingredients)
            }
        }
        CoffeeDrinkFavouriteIcon(
            tint = if (isSystemInDarkTheme()) {
                MaterialTheme.colors.onPrimary
            } else {
                MaterialTheme.colors.primaryVariant
            },
            favouriteState = mutableStateOf(coffeeDrink.isFavourite),
            onValueChanged = { onFavouriteStateChanged(coffeeDrink) }
        )
    }
}

@Composable
private fun CoffeeDrinkLogo(@DrawableRes id: Int) {
    Surface(
        modifier = Modifier.size(COFFEE_DRINK_IMAGE_SIZE)
            .padding(8.dp),
        shape = CircleShape,
        color = Color(0xFFFAFAFA)
    ) {
        Image(
            painter = BitmapPainter(ImageBitmap.imageResource(id = id)),
            modifier = Modifier.fillMaxSize(),
            contentDescription = null
        )
    }
}

@Composable
private fun CoffeeDrinkTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(top = 8.dp, end = 8.dp),
        style = TextStyle(fontSize = 24.sp),
        color = MaterialTheme.colors.onSurface,
        maxLines = 1
    )
}

@Composable
private fun CoffeeDrinkIngredient(ingredients: String) {
    Text(
        text = ingredients,
        modifier = Modifier.padding(end = 8.dp)
            .alpha(0.54f),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onSurface
    )
}

@Composable
private fun CoffeeDrinkFavouriteIcon(
    tint: Color = MaterialTheme.colors.onSurface,
    favouriteState: MutableState<Boolean>,
    onValueChanged: (Boolean) -> Unit
) {
    Favourite(
        state = favouriteState,
        onValueChanged = onValueChanged,
        tint = tint
    )
}

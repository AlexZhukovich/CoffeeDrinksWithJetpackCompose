package com.alexzh.coffeedrinks.ui.screen.coffeedrinks

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.core.drawOpacity
import androidx.ui.core.paint
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ColorPainter
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.Stack
import androidx.ui.layout.StackScope
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.layout.preferredHeight
import androidx.ui.layout.preferredSize
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.appTypography
import com.alexzh.coffeedrinks.ui.component.Favourite
import com.alexzh.coffeedrinks.ui.lightThemeColors
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.mapper.CoffeeDrinkItemMapper
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model.CoffeeDrinkItem

@Preview
@Composable
fun previewDetailedListItem() {
    MaterialTheme(colors = lightThemeColors, typography = appTypography) {
        val repository = RuntimeCoffeeDrinkRepository
        val mapper = CoffeeDrinkItemMapper()
        val coffeeDrink = mapper.map(
            repository.getCoffeeDrinks().first()
        )

        CoffeeDrinkGridCard {
            addFavouriteIcon(coffeeDrink = state { coffeeDrink }, onFavouriteStateChanged = {})
            addTitle(title = coffeeDrink.name)
            addLogo(imageUrl = coffeeDrink.imageUrl)
            addDescription(description = coffeeDrink.description)
        }
    }
}

@Composable
fun CoffeeDrinkDetailedItem(
    coffeeDrink: MutableState<CoffeeDrinkItem>,
    onFavouriteStateChanged: (MutableState<CoffeeDrinkItem>) -> Unit
) {
    CoffeeDrinkGridCard {
        addFavouriteIcon(
            coffeeDrink = coffeeDrink,
            onFavouriteStateChanged = onFavouriteStateChanged
        )
        addTitle(title = coffeeDrink.value.name)
        addLogo(imageUrl = coffeeDrink.value.imageUrl)
        addDescription(description = coffeeDrink.value.description)
    }
}

@Composable
private fun CoffeeDrinkGridCard(
    children: @Composable StackScope.() -> Unit
) {
    Surface(
        color = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(8.dp),
        elevation = 1.dp
    ) {
        Stack(
            modifier = Modifier.preferredHeight(240.dp) + Modifier.fillMaxWidth()
        ) {
            addBackground()
            children()
        }
    }
}

@Composable
private fun addBackground() {
    Box(
        modifier = Modifier.preferredHeight(160.dp) + Modifier.fillMaxWidth() + Modifier.paint(
            ColorPainter(MaterialTheme.colors.primary)
        )
    )
}

@Composable
private fun addFavouriteIcon(
    coffeeDrink: MutableState<CoffeeDrinkItem>,
    onFavouriteStateChanged: (MutableState<CoffeeDrinkItem>) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        gravity = ContentGravity.TopEnd
    ) {
        CoffeeDrinkFavouriteIcon(
            if (isSystemInDarkTheme()) {
                MaterialTheme.colors.onPrimary
            } else {
                MaterialTheme.colors.onPrimary
            },
            favouriteState = coffeeDrink.value.isFavourite,
            onValueChanged = { onFavouriteStateChanged(coffeeDrink) }
        )
    }
}

@Composable
private fun addTitle(title: String) {
    Box(
        modifier = Modifier.preferredHeight(160.dp) + Modifier.fillMaxWidth(),
        gravity = ContentGravity.BottomStart
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
            text = title,
            style = MaterialTheme.typography.h5.copy(
                color = MaterialTheme.colors.onPrimary
            )
        )
    }
}

@Composable
private fun addLogo(imageUrl: Int) {
    Box(
        modifier = Modifier.preferredHeight(164.dp) + Modifier.fillMaxWidth(),
        gravity = ContentGravity.Center
    ) {
        Image(
            painter = ImagePainter(imageResource(id = imageUrl)),
            modifier = Modifier.preferredSize(100.dp)
        )
    }
}

@Composable
private fun addDescription(description: String) {
    Box(
        modifier = Modifier.fillMaxSize() + Modifier.padding(8.dp),
        gravity = ContentGravity.BottomStart
    ) {
        Text(
            text = description,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.drawOpacity(0.54f)
        )
    }
}

@Composable
private fun CoffeeDrinkFavouriteIcon(
    tint: Color = MaterialTheme.colors.onSurface,
    favouriteState: Boolean,
    onValueChanged: (Boolean) -> Unit
) {
    Favourite(
        state = favouriteState,
        modifier = Modifier.drawOpacity(0.78f),
        onValueChanged = onValueChanged,
        favouriteVectorId = R.drawable.ic_baseline_favorite_24,
        nonFavouriteVectorId = R.drawable.ic_baseline_favorite_border_24,
        tint = tint
    )
}

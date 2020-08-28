package com.alexzh.coffeedrinks.ui.screen.coffeedrinks

import androidx.compose.foundation.Box
import androidx.compose.foundation.ContentGravity
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.StackScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.ImagePainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
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
            addFavouriteIcon(coffeeDrink = coffeeDrink, onFavouriteStateChanged = {})
            addTitle(title = coffeeDrink.name)
            addLogo(imageUrl = coffeeDrink.imageUrl)
            addDescription(description = coffeeDrink.description)
        }
    }
}

@Composable
fun CoffeeDrinkDetailedItem(
    coffeeDrink: CoffeeDrinkItem,
    onFavouriteStateChanged: (CoffeeDrinkItem) -> Unit
) {
    CoffeeDrinkGridCard {
        addFavouriteIcon(
            coffeeDrink = coffeeDrink,
            onFavouriteStateChanged = onFavouriteStateChanged
        )
        addTitle(title = coffeeDrink.name)
        addLogo(imageUrl = coffeeDrink.imageUrl)
        addDescription(description = coffeeDrink.description)
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
            modifier = Modifier.preferredHeight(240.dp)
                    .fillMaxWidth()
        ) {
            addBackground()
            children()
        }
    }
}

@Composable
private fun addBackground() {
    Box(
        modifier = Modifier.preferredHeight(160.dp)
                .fillMaxWidth()
                .paint(ColorPainter(MaterialTheme.colors.primary))
    )
}

@Composable
private fun addFavouriteIcon(
    coffeeDrink: CoffeeDrinkItem,
    onFavouriteStateChanged: (CoffeeDrinkItem) -> Unit
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
            favouriteState = coffeeDrink.isFavourite.value,
            onValueChanged = { onFavouriteStateChanged(coffeeDrink) }
        )
    }
}

@Composable
private fun addTitle(title: String) {
    Box(
        modifier = Modifier.preferredHeight(160.dp)
                .fillMaxWidth(),
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
        modifier = Modifier.preferredHeight(164.dp)
                .fillMaxWidth(),
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
        modifier = Modifier.fillMaxSize()
                .padding(8.dp),
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

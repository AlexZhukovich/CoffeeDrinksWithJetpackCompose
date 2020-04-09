package com.alexzh.coffeedrinks.ui.screen.coffeedrinks

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.paint
import androidx.ui.foundation.Border
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ColorPainter
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.res.imageResource
import androidx.ui.res.loadImageResource
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

private val repository = RuntimeCoffeeDrinkRepository

@Preview
@Composable
fun previewListCard() {
    MaterialTheme(colors = lightThemeColors, typography = appTypography) {
        CoffeeDrinkListCard(
            coffeeDrink = CoffeeDrinkItemMapper().map(repository.getCoffeeDrinks()[2]),
            onFavouriteStateChanged = {}
        )
    }
}

@Preview
@Composable
fun previewGridCard() {
    MaterialTheme(colors = lightThemeColors, typography = appTypography) {
        CoffeeDrinkGridCard(
            coffeeDrink = CoffeeDrinkItemMapper().map(repository.getCoffeeDrinks()[2]),
            onFavouriteStateChanged = {}
        )
    }
}

@Composable
fun CoffeeDrinkListCard(
    coffeeDrink: CoffeeDrinkItem,
    onFavouriteStateChanged: (CoffeeDrinkItem) -> Unit
) {
    MaterialTheme(colors = lightThemeColors, typography = appTypography) {
        Column {
            Row {
                CoffeeDrinkLogo(id = coffeeDrink.imageUrl)
                Box(
                    modifier = Modifier.weight(1f),
                    gravity = ContentGravity.TopStart
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
    }
}

@Composable
fun CoffeeDrinkGridCard(
    coffeeDrink: CoffeeDrinkItem,
    onFavouriteStateChanged: (CoffeeDrinkItem) -> Unit
) {
    Surface(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(8.dp), elevation = 1.dp) {
        Stack(
            modifier = Modifier.preferredHeight(260.dp) + Modifier.fillMaxWidth()
        ) {
            // background
            Box(
                modifier = Modifier.preferredHeight(160.dp) + Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize() + Modifier.paint(ColorPainter(MaterialTheme.colors.primaryVariant))
                )
            }

            Box(
                modifier = Modifier.preferredHeight(160.dp) + Modifier.fillMaxWidth(),
                gravity = ContentGravity.BottomStart
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
                    text = coffeeDrink.name,
                    style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.onPrimary
                    )
                )
            }

            // favourite icon
            Box(
                modifier = Modifier.fillMaxWidth(),
                gravity = ContentGravity.TopEnd
            ) {
                CoffeeDrinkFavouriteIcon(
                    favouriteState = coffeeDrink.isFavourite,
                    onValueChanged = { onFavouriteStateChanged(coffeeDrink) }
                )
            }

            // coffee drink
            Box(
                modifier = Modifier.preferredHeight(140.dp) + Modifier.fillMaxWidth(),
                gravity = ContentGravity.Center
            ) {
                Surface(
                    color = Color(0xBBFFFFFF),
                    border = Border(size = 1.dp, color = MaterialTheme.colors.surface),
                    shape = CircleShape
                ) {
                    Box(
                        modifier = Modifier.preferredSize(96.dp) + Modifier,
                        gravity = ContentGravity.Center
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize() + Modifier.paint(ImagePainter(imageResource(id = coffeeDrink.imageUrl)))
                        )
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxSize() + Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 8.dp,
                    bottom = 36.dp
                ),
                gravity = ContentGravity.BottomStart
            ) {
                Text(
                    text = coffeeDrink.description,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
fun CoffeeDrinkLogo(@DrawableRes id: Int) {
    Box(modifier = Modifier.preferredSize(80.dp), gravity = ContentGravity.Center) {
        val image = loadImageResource(id = id)
        image.resource.resource?.let {
            Box(
                modifier = Modifier.fillMaxSize() + Modifier.paint(ImagePainter(imageResource(id = id)))
            )
        }
    }
}

@Composable
fun CoffeeDrinkFavouriteIcon(
    favouriteState: Boolean,
    onValueChanged: (Boolean) -> Unit
) {
    Favourite(
        state = favouriteState,
        onValueChanged = onValueChanged,
        favouriteVectorId = R.drawable.ic_baseline_favorite_24,
        nonFavouriteVectorId = R.drawable.ic_baseline_favorite_border_24,
        tint = MaterialTheme.colors.secondaryVariant
    )
}

@Composable
fun CoffeeDrinkTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.h5,
        maxLines = 1
    )
}

@Composable
fun CoffeeDrinkIngredient(ingredients: String) {
    Text(
        text = ingredients,
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.body1
    )
}
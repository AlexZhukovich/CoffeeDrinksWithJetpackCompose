package com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.toModifier
import androidx.ui.foundation.Border
import androidx.ui.foundation.Box
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ColorPainter
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Surface
import androidx.ui.res.loadImageResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.alexzh.jetpackcomposeworkshop.R
import com.alexzh.jetpackcomposeworkshop.data.RuntimeCoffeeDrinkRepository
import com.alexzh.jetpackcomposeworkshop.ui.components.Favourite
import com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks.mapper.CoffeeDrinkItemMapper
import com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks.model.CoffeeDrinkItem

private val repository = RuntimeCoffeeDrinkRepository

@Preview
@Composable
fun previewListCard() {
    CoffeeDrinkListCard(
        coffeeDrink = CoffeeDrinkItemMapper().map(repository.getCoffeeDrinks()[2]),
        onFavouriteStateChanged = {}
    )
}

@Preview
@Composable
fun previewGridCard() {
    CoffeeDrinkGridCard(
        coffeeDrink = CoffeeDrinkItemMapper().map(repository.getCoffeeDrinks()[2]),
        onFavouriteStateChanged = {}
    )
}

@Composable
fun CoffeeDrinkListCard(
    coffeeDrink: CoffeeDrinkItem,
    onFavouriteStateChanged: (CoffeeDrinkItem) -> Unit
) {
    MaterialTheme {
        Column {
            Row {
                CoffeeDrinkLogo(id = coffeeDrink.imageUrl)
                Container(
                    alignment = Alignment.TopStart,
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
    }
}

@Composable
fun CoffeeDrinkGridCard(
    coffeeDrink: CoffeeDrinkItem,
    onFavouriteStateChanged: (CoffeeDrinkItem) -> Unit
) {
    MaterialTheme {
        Surface(color = Color.White, shape = RoundedCornerShape(8.dp), elevation = 1.dp) {
            Stack(
                modifier = LayoutHeight(260.dp) + LayoutWidth.Fill
            ) {
                // background
                Container(
                    modifier = LayoutHeight(160.dp) + LayoutWidth.Fill
                ) {
                    Box(
                        modifier = LayoutHeight.Fill + LayoutWidth.Fill + ColorPainter(
                            Color(
                                0xFF855446
                            )
                        ).toModifier()
                    )
                }

                Container(
                    modifier = LayoutHeight(160.dp) + LayoutWidth.Fill,
                    alignment = Alignment.BottomStart
                ) {
                    Text(
                        modifier = LayoutPadding(start = 8.dp, bottom = 8.dp),
                        text = coffeeDrink.name,
                        style = TextStyle(color = Color.White, fontSize = 24.sp)
                    )
                }

                // favourite icon
                Container(
                    modifier = LayoutWidth.Fill,
                    alignment = Alignment.TopEnd
                ) {
                    CoffeeDrinkFavouriteIcon(
                        favouriteState = coffeeDrink.isFavourite,
                        onValueChanged = { onFavouriteStateChanged(coffeeDrink) }
                    )
                }

                // coffee drink
                Container(
                    modifier = LayoutHeight(140.dp) + LayoutWidth.Fill,
                    alignment = Alignment.Center
                ) {
                    Surface(
                        color = Color(0xBBFFFFFF),
                        border = Border(size = 1.dp, color = Color.White),
                        shape = CircleShape
                    ) {
                        Container(
                            modifier = LayoutSize(96.dp),
                            alignment = Alignment.Center
                        ) {
                            loadImageResource(coffeeDrink.imageUrl).resource.resource?.let {
                                Box(
                                    modifier = LayoutHeight.Fill + LayoutWidth.Fill + ImagePainter(
                                        it
                                    ).toModifier()
                                )
                            }
                        }
                    }
                }

                Container(
                    alignment = Alignment.BottomStart,
                    modifier = LayoutWidth.Fill + LayoutHeight.Fill + LayoutPadding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 8.dp,
                        bottom = 36.dp
                    )
                ) {
                    Text(
                        text = coffeeDrink.description,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3,
                        style = TextStyle(color = Color.Black)
                    )
                }

                Container(
                    alignment = Alignment.BottomEnd,
                    modifier = LayoutWidth.Fill + LayoutHeight.Fill
                ) {
                    Button(
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp
                    ) {
                        Text(text = "Read more", style = TextStyle(Color.Blue))
                    }
                }
            }
        }
    }
}

@Composable
fun CoffeeDrinkLogo(@DrawableRes id: Int) {
    Container(modifier = LayoutSize(80.dp), alignment = Alignment.Center) {
        val image = loadImageResource(id = id)
        image.resource.resource?.let {
            Box(
                modifier = LayoutHeight.Fill + LayoutWidth.Fill + ImagePainter(it).toModifier()
            )
        }
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
        modifier = LayoutPadding(8.dp),
        style = TextStyle(fontSize = 24.sp),
        maxLines = 1
    )
}

@Composable
fun CoffeeDrinkIngredient(ingredients: String) {
    Text(
        text = ingredients,
        modifier = LayoutPadding(start = 8.dp, end = 8.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}
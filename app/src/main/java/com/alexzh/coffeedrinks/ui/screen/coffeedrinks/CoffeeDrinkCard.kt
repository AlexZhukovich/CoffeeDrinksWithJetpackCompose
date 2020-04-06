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
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.component.Favourite
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.mapper.CoffeeDrinkItemMapper
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model.CoffeeDrinkItem

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
    MaterialTheme {
        Surface(color = Color.White, shape = RoundedCornerShape(8.dp), elevation = 1.dp) {
            Stack(
                modifier = Modifier.preferredHeight(260.dp) + Modifier.fillMaxWidth()
            ) {
                // background
                Box(
                    modifier = Modifier.preferredHeight(160.dp) + Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize() + Modifier.paint(ColorPainter(Color(0xFF855446)))
                    )
                }

                Box(
                    modifier = Modifier.preferredHeight(160.dp) + Modifier.fillMaxWidth(),
                    gravity = ContentGravity.BottomStart
                ) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
                        text = coffeeDrink.name,
                        style = TextStyle(color = Color.White, fontSize = 24.sp)
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
                        border = Border(size = 1.dp, color = Color.White),
                        shape = CircleShape
                    ) {
                        Box(
                            modifier = Modifier.preferredSize(96.dp),
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
                        style = TextStyle(color = Color.Black)
                    )
                }

//                Container(
//                    alignment = Alignment.BottomEnd,
//                    modifier = LayoutWidth.Fill + LayoutHeight.Fill
//                ) {
//                    Button(
//                        backgroundColor = Color.Transparent,
//                        elevation = 0.dp
//                    ) {
//                        Text(text = "Read more", style = TextStyle(Color.Blue))
//                    }
//                }
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
        tint = Color.Red
    )
}

@Composable
fun CoffeeDrinkTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(8.dp),
        style = TextStyle(fontSize = 24.sp),
        maxLines = 1
    )
}

@Composable
fun CoffeeDrinkIngredient(ingredients: String) {
    Text(
        text = ingredients,
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}
package com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks

import android.widget.Space
import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Opacity
import androidx.ui.core.RepaintBoundary
import androidx.ui.core.Text
import androidx.ui.foundation.Border
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Surface
import androidx.ui.res.imageResource
import androidx.ui.res.loadImageResource
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
fun previewListCard() {
    CoffeeDrinkListCard(
        coffeeDrink = CoffeeDrinkMapper().map(RuntimeCoffeeDrinkRepository().getCoffeeDrinks()[2]),
        onFavouriteStateChanged = {}
    )
}

@Preview
@Composable
fun previewGridCard() {
    CoffeeDrinkGridCard(
        coffeeDrink = CoffeeDrinkMapper().map(RuntimeCoffeeDrinkRepository().getCoffeeDrinks()[2]),
        onFavouriteStateChanged = {}
    )
}

@Composable
fun CoffeeDrinkListCard(
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
fun CoffeeDrinkGridBackground(resId: Int) {
    RepaintBoundary {
        Container(
            modifier = LayoutHeight(160.dp) + LayoutWidth.Fill
        ) {
            Opacity(opacity = 0.75f) {
                    DrawImage(imageResource(resId))
            }
        }
    }
}

@Composable
fun CoffeeDrinkGridCard(
    coffeeDrink: CoffeeDrinkModel,
    onFavouriteStateChanged: (CoffeeDrinkModel) -> Unit
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
                    Opacity(opacity = 0.75f) {
                        loadImageResource(R.drawable.bg_coffee_bean_2).resource.resource?.let {
                            DrawImage(it)
                        }
                    }
                }

                Container(
                    modifier = LayoutHeight(160.dp) + LayoutWidth.Fill,
                    alignment = Alignment.BottomLeft
                ) {
                    Text(
                        modifier = LayoutPadding(left = 8.dp, bottom = 8.dp),
                        text = coffeeDrink.name,
                        style = TextStyle(color = Color.White, fontSize = 24.sp)
                    )
                }

                // favourite icon
                Container(
                    modifier = LayoutWidth.Fill,
                    alignment = Alignment.TopRight
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
                                DrawImage(it)
                            }
                        }
                    }
                }

                Container(
                    alignment = Alignment.BottomLeft,
                    modifier = LayoutWidth.Fill + LayoutHeight.Fill + LayoutPadding(left = 8.dp, right = 8.dp, top = 8.dp, bottom = 36.dp)
                ) {
                    Text(
                        text = coffeeDrink.description,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3,
                        style = TextStyle(color = Color.Black)
                    )
                }

                Container(
                    alignment = Alignment.BottomRight,
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


//        Column {
//            Row {
////                CoffeeDrinkFavouriteIcon(
////                    favouriteState = coffeeDrink.isFavourite,
////                    onValueChanged = { onFavouriteStateChanged(coffeeDrink) }
////                )
//                Container(
//                    alignment = Alignment.TopLeft,
//                    modifier = LayoutHeight(140.dp) + LayoutWidth.Fill
//                    modifier = LayoutFlexible(1f)
//                ) {
//                    DrawImage(
//                        imageResource(R.drawable.bg_coffee_beans)
//                    )
////                    Column {
////                        CoffeeDrinkTitle(title = coffeeDrink.name)
////                        CoffeeDrinkIngredient(ingredients = coffeeDrink.ingredients)
////                    }
//                }
////                CoffeeDrinkLogo(id = coffeeDrink.imageUrl)
//            }
//        }
//        CoffeeDrinkDescription(
//            description = coffeeDrink.description,
//            show = coffeeDrink.isExtended
//        )
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
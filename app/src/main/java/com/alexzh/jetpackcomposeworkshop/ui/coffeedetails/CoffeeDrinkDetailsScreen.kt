package com.alexzh.jetpackcomposeworkshop.ui.coffeedetails

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Opacity
import androidx.ui.core.Text
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.material.AppBarIcon
import androidx.ui.material.Divider
import androidx.ui.material.FloatingActionButton
import androidx.ui.material.TopAppBar
import androidx.ui.material.surface.Surface
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.alexzh.jetpackcomposeworkshop.R
import com.alexzh.jetpackcomposeworkshop.data.RuntimeCoffeeDrinkRepository
import com.alexzh.jetpackcomposeworkshop.ui.Screen
import com.alexzh.jetpackcomposeworkshop.ui.mapper.CoffeeDrinkMapper
import com.alexzh.jetpackcomposeworkshop.ui.model.CoffeeDrinkModel
import com.alexzh.jetpackcomposeworkshop.ui.navigateTo

@Preview
@Composable
fun previewScreen() {
    val coffeeDrink = RuntimeCoffeeDrinkRepository().getCoffeeDrinks().first()
    val mapper = CoffeeDrinkMapper()
    CoffeeDrinkDetailsScreen(mapper.map(coffeeDrink))
}

@Composable
fun CoffeeDrinkDetailsScreen(coffeeDrink: CoffeeDrinkModel) {
    Column {
        Stack(
            modifier = LayoutHeight(240.dp) + LayoutWidth.Fill
        ) {
            Surface(
                color = Color(0xAAFFFFFF)
            ) {
                Container(
                    modifier = LayoutWidth.Fill + LayoutHeight.Fill,
                    alignment = Alignment.TopCenter
                ) {
                    // background
                    Opacity(opacity = 0.6f) {
                        DrawImage(
                            image = imageResource(id = R.drawable.bg_coffee_bean_2)
                        )
                    }
                }
            }

            TopAppBar(
                title = {
                    Text(
                        text = coffeeDrink.name,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                },
                color = Color.Transparent,
                elevation = 0.dp,
                contentColor = Color.White,
                navigationIcon = {
                    AppBarIcon(
                        icon = ImagePainter(image = imageResource(id = R.drawable.ic_arrow_back_white)),
                        onClick = { navigateTo(Screen.CoffeeDrinks)}
                    )
                }
            )

            Container(
                modifier = LayoutHeight.Fill + LayoutWidth.Fill,
                alignment = Alignment.Center
            ) {
                Container(
                    modifier = LayoutSize(180.dp) + LayoutPadding(top = 20.dp),
                    alignment = Alignment.Center
                ) {
                    DrawImage(image = imageResource(id = coffeeDrink.imageUrl))
                }
            }
        }

        Container(
            modifier = LayoutPadding(right = 16.dp) + LayoutWidth.Fill,
            alignment = Alignment.CenterRight
        ) {
            FloatingActionButton(
                modifier = LayoutPadding(top = (-28).dp),
                icon = imageResource(id = if (coffeeDrink.isFavourite) { R.drawable.ic_favorite_white } else { R.drawable.ic_favorite_border_white }) ,
                shape = CircleShape,
                color = Color(0xFF663E34),
                onClick = { coffeeDrink.isFavourite = !coffeeDrink.isFavourite }
            )
        }

        Container(
            modifier = LayoutPadding(left = 16.dp, top = 8.dp, right = 16.dp, bottom = 8.dp)
        ) {
            Column {
                Text(text = coffeeDrink.description, style = TextStyle(fontSize = 18.sp))
                Divider(height = 8.dp, color = Color.Transparent)
                Text(text = coffeeDrink.ingredients, style = TextStyle(fontSize = 18.sp))
            }
        }
    }
}
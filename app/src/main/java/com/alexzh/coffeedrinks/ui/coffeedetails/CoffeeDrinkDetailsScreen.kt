package com.alexzh.coffeedrinks.ui.coffeedetails

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.toModifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ColorPainter
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.FloatingActionButton
import androidx.ui.material.IconButton
import androidx.ui.material.TopAppBar
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.Screen
import com.alexzh.coffeedrinks.ui.coffeedetails.mapper.CoffeeDrinkDetailMapper
import com.alexzh.coffeedrinks.ui.coffeedetails.model.CoffeeDrinkDetail
import com.alexzh.coffeedrinks.ui.navigateTo

@Preview
@Composable
fun previewScreen() {
    val coffeeDrinkId = 1L
    val repository = RuntimeCoffeeDrinkRepository
    val mapper = CoffeeDrinkDetailMapper()
    CoffeeDrinkDetailsScreen(repository, mapper, coffeeDrinkId)
}

@Composable
fun CoffeeDrinkDetailsScreen(
    repository: CoffeeDrinkRepository,
    mapper: CoffeeDrinkDetailMapper,
    coffeeDrinkId: Long
) {
    val coffeeDrink = mapper.map(repository.getCoffeeDrink(coffeeDrinkId))

    if (coffeeDrink == null) {
        navigateTo(Screen.CoffeeDrinks)
        return
    }

    Column {
        Stack(
            modifier = LayoutHeight(240.dp) + LayoutWidth.Fill
        ) {
            Container(
                modifier = LayoutWidth.Fill + LayoutHeight.Fill,
                alignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = LayoutWidth.Fill + LayoutHeight.Fill + ColorPainter(
                        Color(0xFF855446)
                    ).toModifier()
                )
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
                    IconButton(onClick = { navigateTo(Screen.CoffeeDrinks) }) {
                        Icon(icon = ImagePainter(image = imageResource(id = R.drawable.ic_arrow_back_white)))
                    }
                }
            )

            Container(
                modifier = LayoutHeight.Fill + LayoutWidth.Fill,
                alignment = Alignment.Center
            ) {
                Box(
                    modifier = LayoutSize(180.dp) + LayoutPadding(top = 20.dp) + ImagePainter(
                        imageResource(coffeeDrink.imageUrl)
                    ).toModifier()
                )
            }
        }

        Container(
            modifier = LayoutPadding(end = 16.dp) + LayoutWidth.Fill,
            alignment = Alignment.CenterEnd
        ) {
            FloatingActionButton(
                modifier = LayoutPadding(top = (-28).dp),
                icon = imageResource(
                    id = if (coffeeDrink.isFavourite) {
                        R.drawable.ic_favorite_white
                    } else {
                        R.drawable.ic_favorite_border_white
                    }
                ),
                shape = CircleShape,
                color = Color(0xFF663E34),
                onClick = {
                    onFavouriteStateChanged(repository, coffeeDrink)
                }
            )
        }

        Container(
            modifier = LayoutPadding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
        ) {
            Column {
                Text(text = coffeeDrink.description, style = TextStyle(fontSize = 18.sp))
                Divider(height = 8.dp, color = Color.Transparent)
                Text(text = coffeeDrink.ingredients, style = TextStyle(fontSize = 18.sp))
            }
        }
    }
}

private fun onFavouriteStateChanged(
    repository: CoffeeDrinkRepository,
    coffeeDrink: CoffeeDrinkDetail
) {
    val newFavouriteState = !coffeeDrink.isFavourite
    coffeeDrink.isFavourite = newFavouriteState

    repository.getCoffeeDrink(coffeeDrink.id)?.copy(isFavourite = newFavouriteState)?.let {
        repository.updateCoffeeDrink(it)
    }
}
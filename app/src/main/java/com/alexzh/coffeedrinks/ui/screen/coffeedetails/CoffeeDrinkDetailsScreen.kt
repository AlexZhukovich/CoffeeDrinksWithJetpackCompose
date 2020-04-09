package com.alexzh.coffeedrinks.ui.screen.coffeedetails

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.paint
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ColorPainter
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.Screen
import com.alexzh.coffeedrinks.ui.navigateTo
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.mapper.CoffeeDrinkDetailMapper
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.model.CoffeeDrinkDetail

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
    val headerHeight = 240.dp
    val coffeeDrink = mapper.map(repository.getCoffeeDrink(coffeeDrinkId))

    if (coffeeDrink == null) {
        navigateTo(Screen.CoffeeDrinks)
        return
    }

    Stack {
        Column {
            Stack(
                modifier = Modifier.preferredHeight(headerHeight) + Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize() + Modifier.paint(ColorPainter(Color(0xFF855446)))
                )

                TopAppBar(
                    title = {
                        Text(
                            text = coffeeDrink.name,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = MaterialTheme.typography.h6.copy(color = Color.White)
                        )
                    },
                    color = Color.Transparent,
                    elevation = 0.dp,
                    contentColor = Color.White,
                    navigationIcon = {
                        IconButton(onClick = { navigateTo(Screen.CoffeeDrinks) }) {
                            Icon(
                                painter = ImagePainter(image = imageResource(id = R.drawable.ic_arrow_back_white)),
                                tint = Color.White
                            )
                        }
                    }
                )

                Box(
                    modifier = Modifier.fillMaxSize(),
                    gravity = ContentGravity.Center
                ) {
                    Image(
                            painter = ImagePainter(imageResource(coffeeDrink.imageUrl)),
                            modifier = Modifier.preferredSize(180.dp)
                    )
                }
            }
        }

        Column {
            Box(
                modifier = Modifier.padding(end = 16.dp) + Modifier.fillMaxWidth(),
                gravity = Alignment.CenterEnd
            ) {
                FloatingActionButton(
                    modifier = Modifier.padding(top = headerHeight - 28.dp),
                    shape = CircleShape,
                    backgroundColor = Color(0xFF663E34),
                    onClick = {
                        onFavouriteStateChanged(repository, coffeeDrink)
                    }
                ) {
                    Icon(
                        painter = ImagePainter(imageResource(
                            if (coffeeDrink.isFavourite) {
                                R.drawable.ic_favorite_white
                            } else {
                                R.drawable.ic_favorite_border_white
                            }
                        )),
                        tint = Color.White
                    )
                }
            }
            Box(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 8.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
            ) {
                Column {
                    Text(
                        text = coffeeDrink.description,
                        style = MaterialTheme.typography.body1
                    )
                    Divider(height = 8.dp, color = Color.Transparent)
                    Text(
                        text = coffeeDrink.ingredients,
                        style = MaterialTheme.typography.body1
                    )
                }
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
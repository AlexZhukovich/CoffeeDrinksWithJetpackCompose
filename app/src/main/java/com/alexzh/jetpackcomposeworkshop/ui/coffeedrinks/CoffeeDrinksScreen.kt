package com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks

import androidx.compose.Composable
import androidx.compose.Model
import androidx.ui.core.Text
import androidx.ui.foundation.Icon
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import com.alexzh.jetpackcomposeworkshop.R
import com.alexzh.jetpackcomposeworkshop.data.CoffeeDrinkRepository
import com.alexzh.jetpackcomposeworkshop.ui.Screen
import com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks.mapper.CoffeeDrinkItemMapper
import com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks.model.CoffeeDrinkItem
import com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks.model.CoffeeDrinksModel
import com.alexzh.jetpackcomposeworkshop.ui.navigateTo

private var coffeeDrinks = CoffeeDrinksModel.coffeeDrinks

@Model
data class Status(
    var isExtendedListItem: Boolean
)

val status = Status(false)

@Composable
fun CoffeeDrinksScreen(
    repository: CoffeeDrinkRepository,
    mapper: CoffeeDrinkItemMapper
) {
    coffeeDrinks.clear()
    coffeeDrinks.addAll(
        repository.getCoffeeDrinks().map { mapper.map(it) }
    )

    Column {
        CoffeeDrinkAppBar(status)
        Container {
            CoffeeDrinkList(
                status = status,
                coffeeDrinks = coffeeDrinks,
                onCoffeeDrinkClicked = { onCoffeeDrinkClicked(it) },
                onFavouriteStateChanged = { onCoffeeFavouriteStateChanged(repository, it) }
            )
        }
    }
}

@Composable
fun CoffeeDrinkAppBar(status: Status) {
    TopAppBar(
        title = { Text("Coffee Drinks", style = TextStyle(color = Color.White)) },
        color = Color(0xFF855446),
        actions = {
            IconButton(
                onClick = { status.isExtendedListItem = !status.isExtendedListItem }
            ) {
                Icon(
                    icon = ImagePainter(
                        imageResource(id = if (status.isExtendedListItem) R.drawable.ic_list_white else R.drawable.ic_extended_list_white)
                    ),
                    tint = Color.White
                )
            }
            IconButton(onClick = { navigateTo(Screen.OrderCoffeeDrinks) }) {
                Icon(
                    icon = ImagePainter(imageResource(id = R.drawable.ic_order_white)),
                    tint = Color.White
                )
            }
        }
    )
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        CoffeeDrinkAppBar(Status(true))
    }
}

private fun onCoffeeFavouriteStateChanged(repository: CoffeeDrinkRepository, coffee: CoffeeDrinkItem) {
    val newFavouriteState = !coffee.isFavourite

    val index = coffeeDrinks.indexOf(coffee)
    coffeeDrinks[index].isFavourite = newFavouriteState

    repository.getCoffeeDrink(coffee.id)?.copy(isFavourite = newFavouriteState)?.let {
        repository.updateCoffeeDrink(
            it
        )
    }
}

private fun onCoffeeDrinkClicked(coffee: CoffeeDrinkItem) {
    navigateTo(Screen.CoffeeDrinkDetails(coffee.id))
}
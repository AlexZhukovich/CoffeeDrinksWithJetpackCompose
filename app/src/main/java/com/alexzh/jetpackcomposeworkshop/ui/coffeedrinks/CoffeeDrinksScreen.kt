package com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.compose.Model
import androidx.ui.core.Text
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.graphics.painter.Painter
import androidx.ui.layout.Column
import androidx.ui.material.AppBarIcon
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import com.alexzh.jetpackcomposeworkshop.R
import com.alexzh.jetpackcomposeworkshop.data.RuntimeCoffeeDrinkRepository
import com.alexzh.jetpackcomposeworkshop.ui.Screen
import com.alexzh.jetpackcomposeworkshop.ui.mapper.CoffeeDrinkMapper
import com.alexzh.jetpackcomposeworkshop.ui.model.CoffeeDrinkModel
import com.alexzh.jetpackcomposeworkshop.ui.model.CoffeeDrinksModel
import com.alexzh.jetpackcomposeworkshop.ui.navigateTo

private var coffeeDrinks = CoffeeDrinksModel.coffeeDrinks

@Model
data class Status(
    var isExtendedListItem: Boolean
)

val status = Status(false)

@Composable
fun CoffeeDrinksScreen() {
    val mapper = CoffeeDrinkMapper()

    RuntimeCoffeeDrinkRepository().getCoffeeDrinks().forEach {
        coffeeDrinks.add(mapper.map(it))
    }

    Column {
        CoffeeDrinkAppBar(status)
        CoffeeDrinkList(
            status = status,
            coffeeDrinks = coffeeDrinks,
            onCoffeeDrinkClicked = ::onCoffeeDrinkClicked,
            onFavouriteStateChanged = ::onCoffeeFavouriteStateChanged
        )
    }
}

@Composable
fun CoffeeDrinkAppBar(status: Status) {
    TopAppBar<MenuAction>(
        title = { Text("Coffee Drinks", style = TextStyle(color = Color.White)) },
        color = Color(0xFF855446),
        actionData = listOf(MenuAction.OrderCoffeeDrink, MenuAction.CardType),
        action = { menuAction ->
            if (menuAction is MenuAction.CardType) {
                val painter: Painter = ImagePainter(
                    image = imageResource(id = if (status.isExtendedListItem) menuAction.additionalIcon!! else menuAction.icon)
                )
                AppBarIcon(painter) {
                    status.isExtendedListItem = !status.isExtendedListItem
                }
            }
            if (menuAction is MenuAction.OrderCoffeeDrink) {
                AppBarIcon(ImagePainter(imageResource(id = menuAction.icon))) {
                    navigateTo(Screen.OrderCoffeeDrinks)
                }
            }
        }
    )
}

sealed class MenuAction(
    val label: String,
    @DrawableRes val icon: Int,
    @DrawableRes val additionalIcon: Int? = null
) {
    object CardType : MenuAction("Share", R.drawable.ic_extended_list_white, R.drawable.ic_list_white)
    object OrderCoffeeDrink : MenuAction("Order", R.drawable.ic_order_white)
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        CoffeeDrinkAppBar(Status(true))
    }
}

private fun onCoffeeFavouriteStateChanged(coffee: CoffeeDrinkModel) {
    val index = coffeeDrinks.indexOf(coffee)
    coffeeDrinks[index].isFavourite = !coffee.isFavourite
}

private fun onCoffeeDrinkClicked(coffee: CoffeeDrinkModel) {
    navigateTo(Screen.CoffeeDrinkDetails(coffee))
}
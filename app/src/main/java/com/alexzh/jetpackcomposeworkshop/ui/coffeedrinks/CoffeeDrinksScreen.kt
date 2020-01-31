package com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks

import androidx.compose.Composable
import androidx.ui.layout.Column
import com.alexzh.jetpackcomposeworkshop.data.RuntimeCoffeeDrinkRepository
import com.alexzh.jetpackcomposeworkshop.ui.mapper.CoffeeDrinkMapper
import com.alexzh.jetpackcomposeworkshop.ui.model.CoffeeDrinkModel
import com.alexzh.jetpackcomposeworkshop.ui.model.CoffeeDrinksModel

private var coffeeDrinks = CoffeeDrinksModel.coffeeDrinks

@Composable
fun CoffeeDrinksScreen() {
    val mapper = CoffeeDrinkMapper()

    RuntimeCoffeeDrinkRepository().getCoffeeDrinks().forEach {
        coffeeDrinks.add(mapper.map(it))
    }

    Column {
        CoffeeDrinkList(
            coffeeDrinks = coffeeDrinks,
            onCoffeeDrinkClicked = ::onCoffeeDrinkClicked,
            onFavouriteStateChanged = ::onCoffeeFavouriteStateChanged
        )
    }
}

private fun onCoffeeFavouriteStateChanged(coffee: CoffeeDrinkModel) {
    val index = coffeeDrinks.indexOf(coffee)
    coffeeDrinks[index] = coffee.copy(isFavourite = !coffee.isFavourite)
}

private fun onCoffeeDrinkClicked(coffee: CoffeeDrinkModel) {
    for (index in 0..coffeeDrinks.lastIndex) {
        if (coffeeDrinks[index].isExtended) {
            coffeeDrinks[index] = coffeeDrinks[index].copy(isExtended = false)
        }
    }

    val index = coffeeDrinks.indexOf(coffee)
    if (index != -1) {
        coffeeDrinks[index] = coffee.copy(isExtended = !coffee.isExtended)
    }
}
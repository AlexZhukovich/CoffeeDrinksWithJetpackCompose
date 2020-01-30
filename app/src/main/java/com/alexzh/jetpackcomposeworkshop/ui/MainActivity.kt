package com.alexzh.jetpackcomposeworkshop.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import com.alexzh.jetpackcomposeworkshop.data.RuntimeCoffeeDrinkRepository
import com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks.CoffeeDrinkList
import com.alexzh.jetpackcomposeworkshop.ui.mapper.CoffeeDrinkMapper
import com.alexzh.jetpackcomposeworkshop.ui.model.CoffeeDrinkModel
import com.alexzh.jetpackcomposeworkshop.ui.model.CoffeeDrinksModel

class MainActivity : AppCompatActivity() {
    private val mapper = CoffeeDrinkMapper()
    private var coffeeDrinks = CoffeeDrinksModel.coffeeDrinks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RuntimeCoffeeDrinkRepository().getCoffeeDrinks().forEach {
                coffeeDrinks.add(mapper.map(it))
            }

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
}

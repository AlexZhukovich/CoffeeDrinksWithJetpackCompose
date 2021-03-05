package com.alexzh.coffeedrinks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexzh.coffeedrinks.R
import java.lang.IllegalArgumentException

enum class Screen {
    CoffeeDrinks,
    CoffeeDrinkDetails,
    OrderCoffeeDrinks
}

fun Fragment.navigate(from: Screen, to: Screen, bundle: Bundle? = null) {
    val id = mapScreenToId(from, to)
    if (bundle == null) {
        findNavController().navigate(id)
    } else {
        findNavController().navigate(id, bundle)
    }
}

fun Fragment.navigateToPreviousScreen(from: Screen, to: Screen) {
    val id = mapScreenToId(from, to)
    findNavController().popBackStack(id, false)
}

private fun mapScreenToId(from: Screen, to: Screen): Int {
    if (to == from) {
        throw IllegalArgumentException("Cannot navigate from $from to $to")
    }
    return when (to) {
        Screen.CoffeeDrinks -> R.id.coffeeDrinksFragment
        Screen.CoffeeDrinkDetails -> R.id.coffeeDrinkDetailsFragment
        Screen.OrderCoffeeDrinks -> R.id.orderCoffeeDrinkFragment
    }
}

package com.alexzh.coffeedrinks.ui.screen.coffeedetails.model

import androidx.compose.runtime.MutableState

data class CoffeeDrinkDetail(
    val id: Long,
    val name: String,
    val imageUrl: Int,
    val ingredients: String,
    val description: String,
    var isFavourite: MutableState<Boolean>
)

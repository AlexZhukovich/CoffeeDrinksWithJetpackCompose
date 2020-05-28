package com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model

import androidx.compose.MutableState

data class CoffeeDrinkItem(
    val id: Long,
    val name: String,
    val imageUrl: Int,
    val ingredients: String,
    val description: String,
    var isFavourite: MutableState<Boolean>
)

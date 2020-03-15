package com.alexzh.jetpackcomposeworkshop.ui.coffeedetails.model

import androidx.compose.Model

@Model
data class CoffeeDrinkDetail(
    val id: Long,
    val name: String,
    val imageUrl: Int,
    val backgroundImage: Int,
    val ingredients: String,
    val description: String,
    var isFavourite: Boolean
)
package com.alexzh.jetpackcomposeworkshop.data

import androidx.annotation.DrawableRes

data class CoffeeDrink(
    val id: Long,
    val name: String,
    @DrawableRes val imageUrl: Int,
    @DrawableRes val backgroundImage: Int,
    val description: String,
    val ingredients: String,
    val orderDescription: String,
    val price: Double,
    var isFavourite: Boolean
)
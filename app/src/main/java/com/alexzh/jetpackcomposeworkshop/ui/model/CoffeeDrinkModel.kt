package com.alexzh.jetpackcomposeworkshop.ui.model

import androidx.compose.Model

@Model
data class CoffeeDrinkModel(
    val id: Long,
    val name: String,
    val imageUrl: Int,
    val backgroundImage: Int,
    val ingredients: String,
    val description: String,
    var isFavourite: Boolean,
    val isExtended: Boolean
)
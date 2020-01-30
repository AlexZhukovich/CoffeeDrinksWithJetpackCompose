package com.alexzh.jetpackcomposeworkshop.ui.model

import androidx.compose.Model

@Model
data class CoffeeDrinkModel(
    val id: Long,
    val name: String,
    val imageUrl: Int,
    val ingredients: String,
    val description: String,
    val isFavourite: Boolean,
    val isExtended: Boolean
)
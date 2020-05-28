package com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model

import androidx.compose.MutableState
import androidx.compose.mutableStateOf

data class CardType(
    var isDetailedCard: MutableState<Boolean> =
        mutableStateOf(false)
)

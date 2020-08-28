package com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class CardType(
    var isDetailedCard: MutableState<Boolean> =
        mutableStateOf(false)
)

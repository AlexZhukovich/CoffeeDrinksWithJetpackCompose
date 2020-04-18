package com.alexzh.coffeedrinks.ui

import androidx.ui.graphics.Color
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette

val lightThemeColors  = lightColorPalette(
    primary = Color(0xFF663e34),
    primaryVariant = Color(0xFF562a1f),
    secondary = Color(0xFF855446),
    secondaryVariant = Color(0xFFb68171),
    background = Color.White,
    surface = Color.White,
    error = Color(0xFFB00020),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White
)

val darkThemeColors = darkColorPalette(
    primary = Color(0xFF3E2723),
    primaryVariant = Color(0xFF4E342E),
    secondary = Color(0xFF654D46),
    background = Color(0xFF121212),
    surface = Color(0xFF3E2723),
    error = Color(0xFFCF6679),
    onPrimary = Color(0xDEFFFFFF),
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color(0xDEFFFFFF),
    onError = Color.Black
)
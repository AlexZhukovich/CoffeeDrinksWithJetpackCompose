package com.alexzh.jetpackcomposeworkshop.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.WithDensity
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.DrawVector
import androidx.ui.layout.Container
import androidx.ui.layout.LayoutSize
import androidx.ui.res.vectorResource

@Composable
fun VectorImage(
    modifier: Modifier = Modifier.None, @DrawableRes id: Int,
    tint: Color = Color.Transparent
) {
    val vector = vectorResource(id)
    WithDensity {
        Container(modifier = LayoutSize(vector.defaultWidth, vector.defaultHeight)) {
            DrawVector(vector, tint)
        }
    }
}
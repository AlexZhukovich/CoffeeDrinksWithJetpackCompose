package com.alexzh.jetpackcomposeworkshop.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.DrawVector
import androidx.ui.layout.Container
import androidx.ui.layout.LayoutSize
import androidx.ui.res.vectorResource

@Composable
fun VectorImage(
    @DrawableRes id: Int,
    modifier: Modifier = Modifier.None,
    tint: Color = Color.Transparent
) {
    val vector = vectorResource(id)
    DensityAmbient.current.run {
        Container(modifier = LayoutSize(vector.defaultWidth, vector.defaultHeight) + modifier) {
            DrawVector(vector, tint)
        }
    }
}
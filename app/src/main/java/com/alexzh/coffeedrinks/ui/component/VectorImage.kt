package com.alexzh.coffeedrinks.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.paint
import androidx.ui.foundation.Box
import androidx.ui.graphics.Color
import androidx.ui.graphics.ColorFilter
import androidx.ui.graphics.vector.VectorPainter
import androidx.ui.layout.preferredSize
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp

@Composable
fun VectorImage(
    @DrawableRes id: Int,
    modifier: Modifier = Modifier.preferredSize(48.dp),
    tint: Color = Color.Transparent
) {
    Box(
        modifier = modifier +
                Modifier.paint(
                    VectorPainter(asset = vectorResource(id = id)),
                    colorFilter = ColorFilter.tint(tint)
                )
    )
}

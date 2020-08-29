package com.alexzh.coffeedrinks.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Box
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun VectorImage(
    @DrawableRes id: Int,
    modifier: Modifier = Modifier.preferredSize(48.dp),
    tint: Color = Color.Transparent
) {
    Box(
        modifier = modifier
                .paint(
                    VectorPainter(asset = vectorResource(id = id)),
                    colorFilter = ColorFilter.tint(tint)
                )
    )
}

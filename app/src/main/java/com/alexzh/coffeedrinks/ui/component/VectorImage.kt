package com.alexzh.coffeedrinks.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun VectorImage(
    @DrawableRes id: Int,
    tint: Color = Color.Transparent
) {
    Box(
        modifier = Modifier.size(48.dp)
            .paint(
                rememberVectorPainter(image = ImageVector.vectorResource(id = id)),
                colorFilter = ColorFilter.tint(tint)
            )
    )
}

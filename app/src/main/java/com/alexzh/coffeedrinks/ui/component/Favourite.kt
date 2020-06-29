package com.alexzh.coffeedrinks.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.selection.toggleable
import androidx.ui.graphics.Color
import androidx.ui.layout.preferredSize
import androidx.ui.unit.dp

@Composable
fun Favourite(
    state: Boolean,
    modifier: Modifier = Modifier,
    onValueChanged: (Boolean) -> Unit,
    @DrawableRes favouriteVectorId: Int,
    @DrawableRes nonFavouriteVectorId: Int,
    tint: Color = Color.Transparent
) {
    Box(modifier = modifier +
            Modifier.preferredSize(48.dp) +
            Modifier.toggleable(value = state, onValueChange = onValueChanged)
    ) {
        if (state) {
            VectorImage(
                id = favouriteVectorId,
                tint = tint
            )
        } else {
            VectorImage(
                id = nonFavouriteVectorId,
                tint = tint
            )
        }
    }
}

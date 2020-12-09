package com.alexzh.coffeedrinks.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Favourite(
    state: Boolean,
    modifier: Modifier = Modifier,
    onValueChanged: (Boolean) -> Unit,
    @DrawableRes favouriteVectorId: Int,
    @DrawableRes nonFavouriteVectorId: Int,
    tint: Color = Color.Transparent
) {
    Box(modifier = modifier
            .preferredSize(48.dp)
            .toggleable(value = state, onValueChange = onValueChanged)
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

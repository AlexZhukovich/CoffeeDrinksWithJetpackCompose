package com.alexzh.coffeedrinks.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.foundation.selection.Toggleable
import androidx.ui.graphics.Color
import androidx.ui.layout.Container
import androidx.ui.layout.LayoutSize
import androidx.ui.unit.dp

@Composable
fun Favourite(
    state: Boolean,
    onValueChanged: (Boolean) -> Unit,
    @DrawableRes favouriteVectorId: Int,
    @DrawableRes nonFavouriteVectorId: Int,
    tint: Color = Color.Transparent
) {
    Toggleable(
        value = state,
        onValueChange = onValueChanged
    ) {
        Container(modifier = LayoutSize(48.dp)) {
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
}
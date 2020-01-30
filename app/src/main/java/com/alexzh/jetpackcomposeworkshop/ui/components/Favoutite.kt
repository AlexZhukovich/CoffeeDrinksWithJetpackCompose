package com.alexzh.jetpackcomposeworkshop.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.dp
import androidx.ui.foundation.selection.Toggleable
import androidx.ui.graphics.Color
import androidx.ui.layout.Container
import androidx.ui.layout.Size

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
        Container(modifier = Size(48.dp, 48.dp)) {
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
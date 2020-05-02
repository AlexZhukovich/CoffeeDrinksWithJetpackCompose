package com.alexzh.coffeedrinks.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.selection.Toggleable
import androidx.ui.graphics.Color
import androidx.ui.layout.preferredSize
import androidx.ui.material.ripple.ripple
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
    Toggleable(
        value = state,
        onValueChange = onValueChanged,
        modifier = Modifier.ripple(radius = 24.dp)
    ) {
        Box(modifier = Modifier.preferredSize(48.dp) + modifier) {
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

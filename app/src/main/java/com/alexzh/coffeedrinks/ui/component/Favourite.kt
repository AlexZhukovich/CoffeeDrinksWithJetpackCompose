package com.alexzh.coffeedrinks.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexzh.coffeedrinks.R

@Composable
fun Favourite(
    state: Boolean,
    modifier: Modifier = Modifier,
    onValueChanged: (Boolean) -> Unit,
    @DrawableRes favouriteVectorId: Int = R.drawable.ic_baseline_favorite_24,
    @DrawableRes nonFavouriteVectorId: Int = R.drawable.ic_baseline_favorite_border_24,
    @StringRes favouriteContentDescription: Int = R.string.mark_as_favorite,
    @StringRes nonFavouriteContentDescription: Int = R.string.unmark_as_favorite,
    tint: Color = Color.Transparent
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(48.dp)
            .toggleable(value = state, onValueChange = onValueChanged)
    ) {
        Icon(
            painter = painterResource(if (state) favouriteVectorId else nonFavouriteVectorId),
            contentDescription = stringResource(
                if (state) favouriteContentDescription else nonFavouriteContentDescription
            ),
            tint = tint
        )
    }
}

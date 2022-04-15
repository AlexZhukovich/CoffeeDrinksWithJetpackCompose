package com.alexzh.coffeedrinks.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexzh.coffeedrinks.R

@Composable
fun Favourite(
    state: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onValueChanged: (Boolean) -> Unit,
    @DrawableRes favouriteVectorId: Int = R.drawable.ic_baseline_favorite_24,
    @DrawableRes nonFavouriteVectorId: Int = R.drawable.ic_baseline_favorite_border_24,
    @StringRes favouriteContentDescription: Int = R.string.mark_as_favorite,
    @StringRes nonFavouriteContentDescription: Int = R.string.unmark_as_favorite,
    tint: Color = Color.Red
) {
    Crossfade(
        modifier = modifier.size(48.dp)
            .padding(8.dp)
            .toggleable(value = state.value, onValueChange = onValueChanged),
        targetState = state.value
    ) { isFavourite ->
        if (isFavourite) {
            Icon(
                painter = painterResource(favouriteVectorId),
                contentDescription = stringResource(favouriteContentDescription),
                modifier = Modifier.fillMaxSize(),
                tint = tint
            )
        } else {
            Icon(
                painter = painterResource(nonFavouriteVectorId),
                contentDescription = stringResource(nonFavouriteContentDescription),
                modifier = Modifier.fillMaxSize(),
                tint = tint
            )
        }
    }
}

@Preview
@Composable
fun Preview_Favourite() {
    val state = remember { mutableStateOf(false) }

    LaunchedEffect("LaunchAnimation") {
        state.value = true
    }
    Favourite(
        state = state,
        modifier = Modifier.size(100.dp),
        onValueChanged = { },
        tint = Color.Red
    )
}

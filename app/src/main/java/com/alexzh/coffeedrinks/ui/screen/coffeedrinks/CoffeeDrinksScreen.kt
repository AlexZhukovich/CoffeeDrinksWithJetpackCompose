package com.alexzh.coffeedrinks.ui.screen.coffeedrinks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ImagePainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.ui.router.Router
import com.alexzh.coffeedrinks.ui.router.RouterDestination
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model.CoffeeDrinkItem
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model.CoffeeDrinksState
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model.DisplayingOptions
import com.alexzh.coffeedrinks.ui.state.UiState

@Composable
fun CoffeeDrinksScreen(
    router: Router,
    viewModel: CoffeeDrinksViewModel
) {
    viewModel.uiState.observeAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> ShowLoadingScreen()
            is UiState.Success -> ShowSuccessScreen(router, uiState.data, viewModel)
            is UiState.Error -> ShowErrorScreen()
        }
    }
}

@Composable
fun ShowLoadingScreen() {
    // TODO: implement it
}

@Composable
fun ShowSuccessScreen(
    router: Router,
    coffeeDrinksState: CoffeeDrinksState,
    viewModel: CoffeeDrinksViewModel
) {
    CoffeeDrinksScreenUI(
        router = router,
        coffeeDrinksState = coffeeDrinksState,
        viewModel = viewModel
    )
}

@Composable
fun ShowErrorScreen() {
    // TODO: implement it
}

@Composable
fun CoffeeDrinksScreenUI(
    router: Router,
    coffeeDrinksState: CoffeeDrinksState,
    viewModel: CoffeeDrinksViewModel
) {
    Surface {
        Column {
            CoffeeDrinkAppBar(
                router,
                coffeeDrinksState.displayingOption,
                onChangeDisplayOption = { viewModel.changeDisplayingOption() }
            )
            CoffeeDrinkList(
                coffeeDrinksState = coffeeDrinksState,
                onCoffeeDrinkClicked = { onCoffeeDrinkClicked(router, it) },
                onFavouriteStateChanged = { viewModel.changeFavouriteState(it) }
            )
        }
    }
}

@Composable
fun CoffeeDrinkAppBar(
    router: Router,
    displayingOption: DisplayingOptions,
    onChangeDisplayOption: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Coffee Drinks",
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onPrimary
                )
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            IconButton(
                onClick = { onChangeDisplayOption() }
            ) {
                Icon(
                    painter = ImagePainter(
                        imageResource(id = if (displayingOption == DisplayingOptions.CARDS) R.drawable.ic_list_white else R.drawable.ic_extended_list_white)
                    ),
                    contentDescription = stringResource(R.string.action_show_detailed_cards),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(onClick = { router.navigateTo(RouterDestination.OrderCoffeeDrinks) }) {
                Icon(
                    painter = ImagePainter(imageResource(id = R.drawable.ic_order_white)),
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = stringResource(R.string.action_order_coffee_drinks)
                )
            }
        }
    )
}

@Composable
fun CoffeeDrinkList(
    coffeeDrinksState: CoffeeDrinksState,
    onCoffeeDrinkClicked: (CoffeeDrinkItem) -> Unit,
    onFavouriteStateChanged: (CoffeeDrinkItem) -> Unit
) {
    LazyColumn {
        items(items = coffeeDrinksState.coffeeDrinks) { coffeeDrink ->
            Box(
                modifier = Modifier.clickable(
                    onClick = {
                        onCoffeeDrinkClicked(coffeeDrink)
                    }
                )
            ) {
                if (coffeeDrinksState.displayingOption == DisplayingOptions.CARDS) {
                    Box(modifier = Modifier.padding(8.dp)) {
                        CoffeeDrinkDetailedItem(
                            coffeeDrink = coffeeDrink,
                            onFavouriteStateChanged = { onFavouriteStateChanged(it) }
                        )
                    }
                } else {
                    CoffeeDrinkList(
                        coffeeDrink = coffeeDrink,
                        onFavouriteStateChanged = { onFavouriteStateChanged(it) }
                    )
                }
            }
        }
    }
}

private fun onCoffeeDrinkClicked(router: Router, coffee: CoffeeDrinkItem) {
    router.navigateTo(RouterDestination.CoffeeDrinkDetails(coffee.id))
}

package com.alexzh.coffeedrinks.ui.screen.coffeedrinks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ImagePainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.appTypography
import com.alexzh.coffeedrinks.ui.lightThemeColors
import com.alexzh.coffeedrinks.ui.router.AppRouter
import com.alexzh.coffeedrinks.ui.router.Router
import com.alexzh.coffeedrinks.ui.router.RouterDestination
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.mapper.CoffeeDrinkItemMapper
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model.CardType
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model.CoffeeDrinkItem
import kotlinx.coroutines.ExperimentalCoroutinesApi

private val coffeeDrinks = mutableStateListOf<CoffeeDrinkItem>()
val cardType = CardType()

@Preview
@Composable
@ExperimentalCoroutinesApi
fun PreviewCoffeeDrinksScreen() {
    MaterialTheme(colors = lightThemeColors, typography = appTypography) {
        CoffeeDrinksScreen(
            router = AppRouter(),
            repository = RuntimeCoffeeDrinkRepository,
            mapper = CoffeeDrinkItemMapper()
        )
    }
}

@Composable
fun CoffeeDrinksScreen(
    router: Router,
    repository: CoffeeDrinkRepository,
    mapper: CoffeeDrinkItemMapper
) {
    coffeeDrinks.clear()
    coffeeDrinks.addAll(
        repository.getCoffeeDrinks().map { mapper.map(it) }
    )

    CoffeeDrinksScreenUI(
        router = router,
        repository = repository,
        coffeeDrinks = coffeeDrinks
    )
}

@Composable
fun CoffeeDrinksScreenUI(
    router: Router,
    repository: CoffeeDrinkRepository,
    coffeeDrinks: SnapshotStateList<CoffeeDrinkItem>
) {
    Surface {
        Column {
            CoffeeDrinkAppBar(router, cardType)
            CoffeeDrinkList(
                cardType = cardType,
                coffeeDrinks = coffeeDrinks,
                onCoffeeDrinkClicked = { onCoffeeDrinkClicked(router, it) },
                onFavouriteStateChanged = {
                    onCoffeeFavouriteStateChanged(
                        repository,
                        it
                    )
                }
            )
        }
    }
}

@Composable
fun CoffeeDrinkAppBar(
    router: Router,
    cardType: CardType
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
                onClick = {
                    cardType.isDetailedCard.value = !cardType.isDetailedCard.value
                }
            ) {
                Icon(
                    painter = ImagePainter(
                        imageResource(id = if (cardType.isDetailedCard.value) R.drawable.ic_list_white else R.drawable.ic_extended_list_white)
                    ),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(onClick = { router.navigateTo(RouterDestination.OrderCoffeeDrinks) }) {
                Icon(
                    painter = ImagePainter(imageResource(id = R.drawable.ic_order_white)),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    )
}

@Composable
fun CoffeeDrinkList(
    cardType: CardType,
    coffeeDrinks: SnapshotStateList<CoffeeDrinkItem>,
    onCoffeeDrinkClicked: (CoffeeDrinkItem) -> Unit,
    onFavouriteStateChanged: (CoffeeDrinkItem) -> Unit
) {
    LazyColumnFor(items = coffeeDrinks) { coffeeDrink ->
        Box(
            modifier = Modifier.clickable(
                onClick = {
                    onCoffeeDrinkClicked(coffeeDrink)
                }
            )
        ) {
            if (cardType.isDetailedCard.value) {
                Box(modifier = Modifier.padding(8.dp)) {
                    CoffeeDrinkDetailedItem(
                        coffeeDrink = coffeeDrink,
                        onFavouriteStateChanged = { onFavouriteStateChanged(it) }
                    )
                }
            } else {
                CoffeeDrinkListItemWithDivider(
                    coffeeDrink = coffeeDrink,
                    onFavouriteStateChanged = { onFavouriteStateChanged(it) }
                )
            }
        }
    }
}

private fun onCoffeeFavouriteStateChanged(
    repository: CoffeeDrinkRepository,
    coffee: CoffeeDrinkItem
) {
    val newFavouriteState = !coffee.isFavourite.value
    coffee.isFavourite.value = newFavouriteState

    repository.getCoffeeDrink(coffee.id)?.copy(isFavourite = newFavouriteState)?.let {
        repository.updateFavouriteState(
            it.id,
            newFavouriteState
        )
    }
}

private fun onCoffeeDrinkClicked(router: Router, coffee: CoffeeDrinkItem) {
    router.navigateTo(RouterDestination.CoffeeDrinkDetails(coffee.id))
}

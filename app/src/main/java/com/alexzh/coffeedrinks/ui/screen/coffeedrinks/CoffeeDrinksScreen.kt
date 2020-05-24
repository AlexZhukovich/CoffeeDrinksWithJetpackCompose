package com.alexzh.coffeedrinks.ui.screen.coffeedrinks

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.frames.ModelList
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Box
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.Column
import androidx.ui.layout.padding
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.material.TopAppBar
import androidx.ui.material.ripple.ripple
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
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

private val coffeeDrinks = ModelList<CoffeeDrinkItem>()

val cardType = CardType(isDetailedCard = false)

@Preview
@Composable
fun previewCoffeeDrinksScreen() {
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
        coffeeDrinks = state { coffeeDrinks }
    )
}

@Composable
fun CoffeeDrinksScreenUI(
    router: Router,
    repository: CoffeeDrinkRepository,
    coffeeDrinks: MutableState<ModelList<CoffeeDrinkItem>>
) {
    val cardTypeState = state { cardType }

    Surface {
        Column {
            CoffeeDrinkAppBar(router, cardTypeState)
            CoffeeDrinkList(
                cardType = cardTypeState,
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
    cardType: MutableState<CardType>
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
                    cardType.value = cardType.value.copy(
                        isDetailedCard = !cardType.value.isDetailedCard
                    )
                }
            ) {
                Icon(
                    painter = ImagePainter(
                        imageResource(id = if (cardType.value.isDetailedCard) R.drawable.ic_list_white else R.drawable.ic_extended_list_white)
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
    cardType: MutableState<CardType>,
    coffeeDrinks: MutableState<ModelList<CoffeeDrinkItem>>,
    onCoffeeDrinkClicked: (CoffeeDrinkItem) -> Unit,
    onFavouriteStateChanged: (MutableState<CoffeeDrinkItem>) -> Unit
) {
    AdapterList(
        data = coffeeDrinks.value
    ) { coffeeDrink ->
        Clickable(
            onClick = { onCoffeeDrinkClicked(coffeeDrink) },
            modifier = Modifier.ripple(bounded = true)
        ) {
            if (cardType.value.isDetailedCard) {
                Box(modifier = Modifier.padding(8.dp)) {
                    val coffeeDrinkState = state { coffeeDrink }
                    CoffeeDrinkDetailedItem(
                        coffeeDrink = state { coffeeDrink },
                        onFavouriteStateChanged = { onFavouriteStateChanged(it) }
                    )
                }
            } else {
                CoffeeDrinkListItemWithDivider(
                    coffeeDrink = state { coffeeDrink },
                    onFavouriteStateChanged = { onFavouriteStateChanged(it) }
                )
            }
        }
    }
}

private fun onCoffeeFavouriteStateChanged(
    repository: CoffeeDrinkRepository,
    coffee: MutableState<CoffeeDrinkItem>
) {
    val newFavouriteState = !coffee.value.isFavourite

    val index = coffeeDrinks.indexOf(coffee.value)

    // TODO: check if needed
    coffeeDrinks[index].isFavourite = newFavouriteState

    coffee.value = coffee.value.copy(isFavourite = newFavouriteState)

    repository.getCoffeeDrink(coffee.value.id)?.copy(isFavourite = newFavouriteState)?.let {
        repository.updateFavouriteState(
            it.id,
            newFavouriteState
        )
    }
}

private fun onCoffeeDrinkClicked(router: Router, coffee: CoffeeDrinkItem) {
    router.navigateTo(RouterDestination.CoffeeDrinkDetails(coffee.id))
}

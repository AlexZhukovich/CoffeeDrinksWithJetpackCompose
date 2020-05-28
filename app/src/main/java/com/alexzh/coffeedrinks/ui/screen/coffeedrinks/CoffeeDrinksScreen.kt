package com.alexzh.coffeedrinks.ui.screen.coffeedrinks

import androidx.compose.Composable
import androidx.compose.frames.ModelList
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
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
val cardType = CardType()

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
        coffeeDrinks = coffeeDrinks
    )
}

@Composable
fun CoffeeDrinksScreenUI(
    router: Router,
    repository: CoffeeDrinkRepository,
    coffeeDrinks: ModelList<CoffeeDrinkItem>
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
    coffeeDrinks: ModelList<CoffeeDrinkItem>,
    onCoffeeDrinkClicked: (CoffeeDrinkItem) -> Unit,
    onFavouriteStateChanged: (CoffeeDrinkItem) -> Unit
) {
    AdapterList(
        data = coffeeDrinks
    ) { coffeeDrink ->
        Box(
            modifier = Modifier.ripple(bounded = true) +
                    Modifier.clickable(
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

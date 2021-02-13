package com.alexzh.coffeedrinks.ui.screen.order

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ImagePainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.data.DummyCoffeeDrinksDataSource
import com.alexzh.coffeedrinks.data.order.OrderCoffeeDrinkMapper
import com.alexzh.coffeedrinks.data.order.RuntimeOrderCoffeeDrinksRepository
import com.alexzh.coffeedrinks.ui.appTypography
import com.alexzh.coffeedrinks.ui.component.AppDivider
import com.alexzh.coffeedrinks.ui.component.Counter
import com.alexzh.coffeedrinks.ui.lightThemeColors
import com.alexzh.coffeedrinks.ui.router.Router
import com.alexzh.coffeedrinks.ui.router.RouterDestination
import com.alexzh.coffeedrinks.ui.screen.order.model.OrderCoffeeDrinkState
import com.alexzh.coffeedrinks.ui.state.UiState
import java.math.BigDecimal
import kotlinx.coroutines.runBlocking

@Preview
@Composable
fun PreviewOrderCoffeeDrinkItem() {
    MaterialTheme(colors = lightThemeColors, typography = appTypography) {
        val repository = RuntimeOrderCoffeeDrinksRepository(
            DummyCoffeeDrinksDataSource(),
            OrderCoffeeDrinkMapper()
        )

        OrderCoffeeDrinkItem(
            orderCoffeeDrink = runBlocking { repository.getCoffeeDrinks() }.first(),
            onAdded = {},
            onRemoved = {}
        )
    }
}

@Composable
fun OrderCoffeeDrinkScreen(
    router: Router,
    viewModel: OrderCoffeeDrinkViewModel
) {
    viewModel.uiState.observeAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> ShowLoadingScreen()
            is UiState.Success -> ShowSuccessScreen(router, uiState.data, viewModel = viewModel)
            is UiState.Error -> ShowErrorScreen()
        }
    }
}

@Composable
private fun ShowLoadingScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .align(Alignment.Center)
                .preferredSize(36.dp)
        )
    }
}

@Composable
private fun ShowSuccessScreen(
    router: Router,
    orderCoffeeDrinkState: OrderCoffeeDrinkState,
    viewModel: OrderCoffeeDrinkViewModel
) {
    Column {
        AppBarWithOrderSummary(router, orderCoffeeDrinkState.totalPrice)
        Surface {
            LazyColumn {
                items(items = orderCoffeeDrinkState.coffeeDrinks) { coffeeDrink ->
                    Column {
                        OrderCoffeeDrinkItem(
                            orderCoffeeDrink = coffeeDrink,
                            onAdded = { coffeeDrinkId -> viewModel.addDrink(coffeeDrinkId) },
                            onRemoved = { coffeeDrinkId -> viewModel.removeDrink(coffeeDrinkId) }
                        )
                        AppDivider(PaddingValues(start = 72.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun ShowErrorScreen() {
    // TODO: implement UI
}

@Composable
private fun AppBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Order coffee drinks",
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onPrimary
                )
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = ImagePainter(imageResource(id = R.drawable.ic_arrow_back_white)),
                    contentDescription = stringResource(R.string.action_back),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    )
}

@Composable
fun OrderCoffeeDrinkItem(
    orderCoffeeDrink: com.alexzh.coffeedrinks.data.order.OrderCoffeeDrink,
    onAdded: (Long) -> Unit,
    onRemoved: (Long) -> Unit
) {
    Box(modifier = Modifier.padding(top = 16.dp, end = 16.dp)) {
        Row {
            Logo(orderCoffeeDrink.imageUrl)
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Column {
                    Text(
                        text = orderCoffeeDrink.name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 8.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = orderCoffeeDrink.ingredients,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            Box(
                modifier = Modifier.preferredWidth(120.dp)
                    .padding(start = 8.dp)
            ) {
                Column {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth(),
                        text = "€ ${orderCoffeeDrink.price}",
                        style = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Right)
                    )
                    Counter(
                        orderCoffeeDrink,
                        onAdded,
                        onRemoved
                    )
                }
            }
        }
    }
}

@Composable
private fun Logo(
    @DrawableRes logoId: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .preferredSize(72.dp)
            .padding(16.dp),
        shape = CircleShape,
        color = Color(0xFFFAFAFA)
    ) {
        Image(
            modifier = Modifier.preferredSize(64.dp),
            painter = ImagePainter(imageResource(id = logoId)),
            contentDescription = null
        )
    }
}

@Composable
private fun AppBarWithOrderSummary(router: Router, totalPrice: BigDecimal) {
    Surface(
        color = MaterialTheme.colors.primary,
        elevation = 4.dp
    ) {
        Column {
            AppBar {
                router.navigateTo(RouterDestination.CoffeeDrinks)
            }
            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Total cost",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.h6.copy(
                        color = MaterialTheme.colors.onPrimary
                    )
                )
                Text(
                    text = "€ $totalPrice",
                    style = MaterialTheme.typography.h6.copy(
                        color = MaterialTheme.colors.onPrimary
                    )
                )
            }
        }
    }
}

package com.alexzh.coffeedrinks.ui.screen.order

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonConstants
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ImagePainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.darkThemeColors
import com.alexzh.coffeedrinks.ui.router.Router
import com.alexzh.coffeedrinks.ui.router.RouterDestination
import com.alexzh.coffeedrinks.ui.screen.order.mapper.OrderCoffeeDrinkMapper
import com.alexzh.coffeedrinks.ui.screen.order.model.OrderCoffeeDrink
import com.alexzh.coffeedrinks.ui.screen.order.model.OrderCoffeeDrinkData

private val coffeeDrinks = mutableStateListOf<OrderCoffeeDrink>()

@Preview
@Composable
fun PreviewCounter() {
    val mapper = OrderCoffeeDrinkMapper()
    val orderCoffeeDrink = mapper.map(RuntimeCoffeeDrinkRepository.getCoffeeDrinks().first())
    MaterialTheme(colors = darkThemeColors) {
        Counter(orderCoffeeDrink = orderCoffeeDrink)
    }
}

@Composable
fun OrderCoffeeDrinkScreen(
    router: Router,
    repository: CoffeeDrinkRepository,
    mapper: OrderCoffeeDrinkMapper
) {
    repository.getCoffeeDrinks()
        .map { mapper.map(it) }
        .forEach { coffeeDrink ->
            if (coffeeDrinks.size == 0 || coffeeDrinks.find { it.id == coffeeDrink.id } == null) {
                coffeeDrinks.add(coffeeDrink)
            }
        }

    val coffeeDrinkOrder = OrderCoffeeDrinkData(coffeeDrinks)

    OrderCoffeeDrinkScreenUI(
        router,
        coffeeDrinkOrder
    )
}

@Composable
fun OrderCoffeeDrinkScreenUI(
    router: Router,
    order: OrderCoffeeDrinkData
) {
    Column {
        AppBarWithOrderSummary(router, order)
        Surface {
            LazyColumnFor(items = order.drinks) { coffeeDrink ->
                Column {
                    OrderCoffeeDrinkItem(orderCoffeeDrink = coffeeDrink)
                    CoffeeDrinkDivider()
                }
            }
        }
    }
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
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    )
}

@Composable
fun OrderCoffeeDrinkItem(
    orderCoffeeDrink: OrderCoffeeDrink
) {
    Box(modifier = Modifier.padding(top = 16.dp, end = 16.dp)) {
        Row {
            Logo(orderCoffeeDrink.imageRes)
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.weight(1f)
                    .padding(start = 8.dp)
            ) {
                Column {
                    Text(
                        text = orderCoffeeDrink.name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = orderCoffeeDrink.description,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            Box(modifier = Modifier.preferredWidth(120.dp)) {
                Column {
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp)
                            .fillMaxWidth(),
                        text = "€ ${orderCoffeeDrink.price}",
                        style = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Right)
                    )
                    Counter(
                        orderCoffeeDrink
                    )
                }
            }
        }
    }
}

@Composable
private fun Logo(@DrawableRes logoId: Int) {
    Surface(
        modifier = Modifier.preferredSize(72.dp)
            .padding(16.dp),
        shape = CircleShape,
        color = Color(0xFFFAFAFA)
    ) {
        Image(
            modifier = Modifier.preferredSize(64.dp),
            painter = ImagePainter(imageResource(id = logoId))
        )
    }
}

@Composable
private fun Counter(
    orderCoffeeDrink: OrderCoffeeDrink
) {
    Surface(
        shape = RoundedCornerShape(size = 5.dp),
        border = BorderStroke(1.dp, Color.Gray),
        color = Color.Transparent
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    modifier = Modifier.preferredWidth(40.dp),
                    colors = ButtonConstants.defaultOutlinedButtonColors(),
                    elevation = ButtonConstants.defaultElevation(0.dp),
                    onClick = { removeCoffeeDrink(orderCoffeeDrink) }
                ) {
                    Text(
                        text = "—",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground
                    )
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = orderCoffeeDrink.count.value.toString(),
                    style = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Center),
                    color = MaterialTheme.colors.onBackground
                )
                Button(
                    modifier = Modifier.preferredWidth(40.dp),
                    colors = ButtonConstants.defaultOutlinedButtonColors(),
                    elevation = ButtonConstants.defaultElevation(0.dp),
                    onClick = { addCoffeeDrink(orderCoffeeDrink) }
                ) {
                    Text(
                        text = "+",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}

@Composable
private fun AppBarWithOrderSummary(router: Router, order: OrderCoffeeDrinkData) {
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
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = MaterialTheme.colors.onPrimary
                    )
                )
                Text(
                    text = "€ ${order.totalPrice}",
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = MaterialTheme.colors.onPrimary
                    )
                )
            }
        }
    }
}

@Composable
private fun CoffeeDrinkDivider() {
    Divider(
        modifier = Modifier.padding(start = 72.dp)
            .alpha(0.12f),
        color = if (isSystemInDarkTheme()) {
            Color.White
        } else {
            Color.Black
        }
    )
}

private fun addCoffeeDrink(
    orderCoffeeDrinkState: OrderCoffeeDrink
) {
    if (orderCoffeeDrinkState.count.value < 99) {
        orderCoffeeDrinkState.count.value++
    }
}

private fun removeCoffeeDrink(
    orderCoffeeDrinkState: OrderCoffeeDrink
) {
    if (orderCoffeeDrinkState.count.value > 0) {
        orderCoffeeDrinkState.count.value--
    }
}

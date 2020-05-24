package com.alexzh.coffeedrinks.ui.screen.order

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.frames.ModelList
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.core.drawOpacity
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Border
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxHeight
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.layout.preferredSize
import androidx.ui.layout.preferredWidth
import androidx.ui.material.Button
import androidx.ui.material.Divider
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.material.TopAppBar
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.router.Router
import com.alexzh.coffeedrinks.ui.router.RouterDestination
import com.alexzh.coffeedrinks.ui.screen.order.mapper.OrderCoffeeDrinkMapper
import com.alexzh.coffeedrinks.ui.screen.order.model.OrderCoffeeDrink
import com.alexzh.coffeedrinks.ui.screen.order.model.OrderCoffeeDrinkData

private val coffeeDrinks = ModelList<OrderCoffeeDrink>()

@Composable
fun OrderCoffeeDrinkScreen(
    router: Router,
    repository: CoffeeDrinkRepository,
    mapper: OrderCoffeeDrinkMapper
) {
    repository.getCoffeeDrinks()
        .map { mapper.map(it) }
        .forEach {
            if (!coffeeDrinks.contains(it)) {
                coffeeDrinks.add(it)
            }
        }

    val coffeeDrinkOrder = OrderCoffeeDrinkData(coffeeDrinks)

    OrderCoffeeDrinkScreenUI(
        router,
        state { coffeeDrinkOrder }
    )
}

@Composable
fun OrderCoffeeDrinkScreenUI(
    router: Router,
    order: MutableState<OrderCoffeeDrinkData>
) {
    Column {
        AppBarWithOrderSummary(router, order)
        Surface {
            AdapterList(data = order.value.drinks) { coffeeDrink ->
                Column {
                    OrderCoffeeDrinkItem(orderCoffeeDrink = state { coffeeDrink })
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
    orderCoffeeDrink: MutableState<OrderCoffeeDrink>
) {
    Box(modifier = Modifier.padding(top = 16.dp, end = 16.dp)) {
        Row {
            Logo(orderCoffeeDrink.value)
            Box(
                modifier = Modifier.weight(1f) + Modifier.padding(start = 8.dp),
                gravity = ContentGravity.CenterStart
            ) {
                Column {
                    Text(
                        text = orderCoffeeDrink.value.name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = orderCoffeeDrink.value.description,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            Box(modifier = Modifier.preferredWidth(100.dp)) {
                Column {
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp) + Modifier.fillMaxWidth(),
                        text = "€ ${orderCoffeeDrink.value.price}",
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
private fun Logo(orderCoffeeDrink: OrderCoffeeDrink) {
    Surface(
        modifier = Modifier.preferredSize(72.dp) + Modifier.padding(16.dp),
        shape = CircleShape,
        color = Color(0xFFFAFAFA)
    ) {
        Image(
            modifier = Modifier.preferredSize(64.dp),
            painter = ImagePainter(imageResource(id = orderCoffeeDrink.imageRes))
        )
    }
}

@Composable
private fun Counter(
    orderCoffeeDrink: MutableState<OrderCoffeeDrink>
) {
    Surface(
        shape = RoundedCornerShape(size = 5.dp),
        border = Border(1.dp, Color.Gray),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            gravity = ContentGravity.Center
        ) {
            Row {
                Button(
                    modifier = Modifier.preferredWidth(40.dp) + Modifier.fillMaxHeight(),
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    onClick = { removeCoffeeDrink(orderCoffeeDrink) }
                ) {
                    Text(text = "—", style = MaterialTheme.typography.body1)
                }
                Text(
                    modifier = Modifier.weight(1f) + Modifier.padding(top = 8.dp, bottom = 8.dp),
                    text = orderCoffeeDrink.value.count.toString(),
                    style = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Center)
                )
                Button(
                    modifier = Modifier.preferredWidth(40.dp) + Modifier.fillMaxHeight(),
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    onClick = { addCoffeeDrink(orderCoffeeDrink) }
                ) {
                    Text(text = "＋", style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}

@Composable
private fun AppBarWithOrderSummary(router: Router, order: MutableState<OrderCoffeeDrinkData>) {
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
                    text = "€ ${order.value.totalPrice}",
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
        modifier = Modifier.padding(start = 72.dp) + Modifier.drawOpacity(0.12f),
        color = if (isSystemInDarkTheme()) {
            Color.White
        } else {
            Color.Black
        }
    )
}

private fun addCoffeeDrink(
    orderCoffeeDrinkState: MutableState<OrderCoffeeDrink>
) {
    if (orderCoffeeDrinkState.value.count < 99) {
        val newValue = orderCoffeeDrinkState.value.count + 1
        orderCoffeeDrinkState.value = orderCoffeeDrinkState.value.copy(count = newValue)
    }
}

private fun removeCoffeeDrink(orderCoffeeDrinkState: MutableState<OrderCoffeeDrink>) {
    if (orderCoffeeDrinkState.value.count > 0) {
        val newValue = orderCoffeeDrinkState.value.count - 1
        orderCoffeeDrinkState.value = orderCoffeeDrinkState.value.copy(count = newValue)
    }
}

package com.alexzh.coffeedrinks.ui.screen.order

import androidx.compose.Composable
import androidx.compose.frames.ModelList
import androidx.ui.core.Modifier
import androidx.ui.core.drawOpacity
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.Screen
import com.alexzh.coffeedrinks.ui.navigateTo
import com.alexzh.coffeedrinks.ui.screen.order.mapper.OrderCoffeeDrinkMapper
import com.alexzh.coffeedrinks.ui.screen.order.model.OrderCoffeeDrink
import com.alexzh.coffeedrinks.ui.screen.order.model.OrderCoffeeDrinkData

private val coffeeDrinks = ModelList<OrderCoffeeDrink>()

@Composable
fun OrderCoffeeDrinkScreen(
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

    Column {
        AppBarWithOrderSummary(coffeeDrinkOrder.totalPrice)
        Surface {
            AdapterList(data = coffeeDrinks) { coffeeDrink ->
                Column {
                    OrderCoffeeDrinkItem(
                        orderCoffeeDrink = coffeeDrink,
                        onAddCoffeeDrink = { addCoffeeDrink(it) },
                        onRemoveCoffeeDrink = { removeCoffeeDrink(it) }
                    )
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
        backgroundColor = MaterialTheme.colors.primaryVariant,
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
    orderCoffeeDrink: OrderCoffeeDrink,
    onAddCoffeeDrink: (OrderCoffeeDrink) -> Unit,
    onRemoveCoffeeDrink: (OrderCoffeeDrink) -> Unit
) {
    Box(modifier = Modifier.padding(top = 16.dp, end = 16.dp)) {
        Row {
            Logo(orderCoffeeDrink)
            Box(
                modifier = Modifier.weight(1f) + Modifier.padding(start = 8.dp),
                gravity = ContentGravity.CenterStart
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
            Box(modifier = Modifier.preferredWidth(100.dp)) {
                Column {
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp) + Modifier.fillMaxWidth(),
                        text = "€ ${orderCoffeeDrink.price}",
                        style = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Right)
                    )
                    Counter(
                        orderCoffeeDrink,
                        onAddCoffeeDrink,
                        onRemoveCoffeeDrink
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
    orderCoffeeDrink: OrderCoffeeDrink,
    onAddCoffeeDrink: (OrderCoffeeDrink) -> Unit,
    onRemoveCoffeeDrink: (OrderCoffeeDrink) -> Unit
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
                    onClick = { onRemoveCoffeeDrink(orderCoffeeDrink) }
                ) {
                    Text(text = "—", style = MaterialTheme.typography.body1)
                }
                Text(
                    modifier = Modifier.weight(1f) + Modifier.padding(top = 8.dp, bottom = 8.dp),
                    text = orderCoffeeDrink.count.toString(),
                    style = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Center)
                )
                Button(
                    modifier = Modifier.preferredWidth(40.dp),
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    onClick = { onAddCoffeeDrink(orderCoffeeDrink) }
                ) {
                    Text(text = "＋", style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}

@Composable
private fun AppBarWithOrderSummary(totalPrice: Double) {
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        elevation = 4.dp
    ) {
        Column {
            AppBar {
                navigateTo(Screen.CoffeeDrinks)
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
                    text = "€ $totalPrice",
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

private fun addCoffeeDrink(orderCoffeeDrink: OrderCoffeeDrink) {
    if (orderCoffeeDrink.count < 99) {
        orderCoffeeDrink.count++
    }
}

private fun removeCoffeeDrink(orderCoffeeDrink: OrderCoffeeDrink) {
    if (orderCoffeeDrink.count > 0) {
        orderCoffeeDrink.count--
    }
}
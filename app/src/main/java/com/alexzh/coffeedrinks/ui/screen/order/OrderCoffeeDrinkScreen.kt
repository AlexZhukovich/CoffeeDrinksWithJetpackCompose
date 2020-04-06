package com.alexzh.coffeedrinks.ui.screen.order

import androidx.compose.Composable
import androidx.compose.frames.ModelList
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.IconButton
import androidx.ui.material.Surface
import androidx.ui.material.TopAppBar
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.Screen
import com.alexzh.coffeedrinks.ui.navigateTo
import com.alexzh.coffeedrinks.ui.screen.order.mapper.OrderCoffeeDrinkMapper
import com.alexzh.coffeedrinks.ui.screen.order.model.OrderCoffeeDrink
import com.alexzh.coffeedrinks.ui.screen.order.model.OrderCoffeeDrinkData

private val coffeeDrinks = ModelList<OrderCoffeeDrink>()

@Preview
@Composable
fun cardPreview() {
    val repository = RuntimeCoffeeDrinkRepository
    val mapper = OrderCoffeeDrinkMapper()
    OrderCoffeeDrinkScreen(repository, mapper)
}

@Composable
fun OrderCoffeeDrinkScreen(
    repository: CoffeeDrinkRepository,
    mapper: OrderCoffeeDrinkMapper
) {
    coffeeDrinks.addAll((repository.getCoffeeDrinks().map { mapper.map(it) }))
    val coffeeDrinkOrder = OrderCoffeeDrinkData(coffeeDrinks)

    Column {
        AppBar {
            navigateTo(Screen.CoffeeDrinks)
        }
        OrderSummary(coffeeDrinkOrder.totalPrice)
        Box {
            AdapterList(data = coffeeDrinks) { coffeeDrink ->
                OrderCoffeeDrinkItem(
                    orderCoffeeDrink = coffeeDrink,
                    onAddCoffeeDrink = { addCoffeeDrink(it) },
                    onRemoveCoffeeDrink = { removeCoffeeDrink(it) }
                )
            }
        }
    }
}

@Composable
private fun AppBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "Order coffee drinks", style = TextStyle(color = Color.White, fontSize = 18.sp)) },
        color = Color(0xFF855446),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = ImagePainter(imageResource(id = R.drawable.ic_arrow_back_white)),
                    tint = Color.White
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
    Box(modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)) {
        Row {
            Logo(orderCoffeeDrink)
            Box(
                modifier = Modifier.weight(1f) + Modifier.padding(start = 8.dp),
                gravity = ContentGravity.CenterStart
            ) {
                Column {
                    Text(
                        text = orderCoffeeDrink.name,
                        style = TextStyle(fontSize = 24.sp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = orderCoffeeDrink.description,
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }
            Box(modifier = Modifier.preferredWidth(100.dp)) {
                Column {
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp) + Modifier.fillMaxWidth(),
                        text = "€ ${orderCoffeeDrink.price}",
                        style = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Right)
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
    Image(
        modifier = Modifier.preferredSize(64.dp),
        painter = ImagePainter(imageResource(id = orderCoffeeDrink.imageRes))
    )
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
                        Text(text = "—", style = TextStyle(fontSize = 14.sp))
                    }
                Text(
                    modifier = Modifier.weight(1f) + Modifier.padding(top = 6.dp, bottom = 8.dp),
                    text = orderCoffeeDrink.count.toString(),
                    style = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center)
                )
                    Button(
                        modifier = Modifier.preferredWidth(40.dp),
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp,
                        onClick = { onAddCoffeeDrink(orderCoffeeDrink) }
                    ) {
                        Text(text = "＋", style = TextStyle(fontSize = 14.sp))
                    }
            }
        }
    }
}

@Composable
private fun OrderSummary(totalPrice: Double) {
    Surface(
        color = Color(0xFF855446),
        elevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Total cost",
                modifier = Modifier.weight(1f),
                style = TextStyle(fontSize = 18.sp, color = Color.White)
            )
            Text(
                text = "€ $totalPrice",
                style = TextStyle(fontSize = 18.sp, color = Color.White)
            )
        }
    }
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
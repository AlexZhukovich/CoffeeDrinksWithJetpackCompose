package com.alexzh.coffeedrinks.ui.order

import androidx.compose.Composable
import androidx.compose.frames.ModelList
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.toModifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.IconButton
import androidx.ui.material.TopAppBar
import androidx.ui.material.surface.Surface
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.Screen
import com.alexzh.coffeedrinks.ui.navigateTo
import com.alexzh.coffeedrinks.ui.order.mapper.OrderCoffeeDrinkMapper
import com.alexzh.coffeedrinks.ui.order.model.OrderCoffeeDrink
import com.alexzh.coffeedrinks.ui.order.model.OrderCoffeeDrinkData

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
        TopAppBar(
            title = { Text(text = "Order coffee drinks", style = TextStyle(color = Color.White)) },
            color = Color(0xFF855446),
            navigationIcon = {
                IconButton(onClick = { navigateTo(Screen.CoffeeDrinks) }) {
                    Icon(
                        icon = ImagePainter(imageResource(id = R.drawable.ic_arrow_back_white)),
                        tint = Color.White
                    )
                }
            }
        )
        OrderSummary(coffeeDrinkOrder.totalPrice)
        Container {
            AdapterList(data = coffeeDrinks) { coffeeDrink ->
                OrderCoffeeDrinkCard(
                    orderCoffeeDrink = coffeeDrink,
                    onAddCoffeeDrink = { addCoffeeDrink(it) },
                    onRemoveCoffeeDrink = { removeCoffeeDrink(it) }
                )
            }
        }
    }
}

@Composable
fun OrderCoffeeDrinkCard(
    orderCoffeeDrink: OrderCoffeeDrink,
    onAddCoffeeDrink: (OrderCoffeeDrink) -> Unit,
    onRemoveCoffeeDrink: (OrderCoffeeDrink) -> Unit
) {
    Container(padding = EdgeInsets(left = 16.dp, top = 8.dp, right = 16.dp, bottom = 8.dp)) {
        Row {
            Logo(orderCoffeeDrink)
            Container(
                modifier = LayoutFlexible(1f) + LayoutPadding(start = 8.dp),
                alignment = Alignment.CenterStart
            ) {
                Column {
                    Text(
                        text = orderCoffeeDrink.name,
                        style = TextStyle(fontSize = 24.sp),
                        modifier = LayoutPadding(bottom = 8.dp)
                    )
                    Text(
                        text = orderCoffeeDrink.description,
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }
            Container(width = 100.dp) {
                Column {
                    Text(
                        modifier = LayoutPadding(bottom = 4.dp) + LayoutWidth.Fill,
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
    Container(modifier = LayoutSize(64.dp)) {
        Box(
            modifier = ImagePainter(imageResource(id = orderCoffeeDrink.imageRes)).toModifier()
        )
    }
}

@Composable
fun Counter(
    orderCoffeeDrink: OrderCoffeeDrink,
    onAddCoffeeDrink: (OrderCoffeeDrink) -> Unit,
    onRemoveCoffeeDrink: (OrderCoffeeDrink) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(size = 5.dp),
        border = Border(1.dp, Color.Gray),
        color = Color.Transparent
    ) {
        Container(
            modifier = LayoutHeight.Fill + LayoutWidth.Fill,
            alignment = Alignment.Center
        ) {
            Row {
                Clickable {
                    Button(
                        modifier = LayoutWidth(40.dp) + LayoutHeight.Fill,
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp,
                        onClick = { onRemoveCoffeeDrink(orderCoffeeDrink) }
                    ) {
                        Text(text = "—", style = TextStyle(fontSize = 14.sp))
                    }
                }
                Text(
                    modifier = LayoutFlexible(1f) + LayoutPadding(top = 6.dp, bottom = 8.dp),
                    text = orderCoffeeDrink.count.toString(),
                    style = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center)
                )
                Clickable {
                    Button(
                        modifier = LayoutWidth(40.dp),
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
}

@Composable
fun OrderSummary(totalPrice: Double) {
    Surface(color = Color(0xFF855446)) {
        Row(modifier = LayoutPadding(16.dp)) {
            Text(
                text = "Total cost",
                modifier = LayoutFlexible(1f),
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
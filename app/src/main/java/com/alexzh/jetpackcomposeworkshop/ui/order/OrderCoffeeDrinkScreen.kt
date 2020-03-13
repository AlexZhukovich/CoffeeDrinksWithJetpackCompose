package com.alexzh.jetpackcomposeworkshop.ui.order

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.compose.Model
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
import androidx.ui.res.loadImageResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.alexzh.jetpackcomposeworkshop.R
import com.alexzh.jetpackcomposeworkshop.ui.Screen
import com.alexzh.jetpackcomposeworkshop.ui.navigateTo

private val coffeeDrinks = ModelList<OrderCoffeeDrink>().apply {
    addAll(
        listOf(
            OrderCoffeeDrink("Americano", R.drawable.americano_small, "150 ml", 7.0, 0),
            OrderCoffeeDrink("Cappuccino", R.drawable.cappuccino_small, "250 ml", 6.0, 1),
            OrderCoffeeDrink("Espresso", R.drawable.espresso_small, "200 ml", 5.0, 1),
            OrderCoffeeDrink("Espresso Macchiato", R.drawable.espresso_macchiato_small, "300 ml", 8.0, 0),
            OrderCoffeeDrink("Frappino", R.drawable.frappino_small, "400 ml", 8.0, 0),
            OrderCoffeeDrink("Iced Mocha", R.drawable.iced_mocha_small, "400 ml", 9.0, 0),
            OrderCoffeeDrink("Irish coffee", R.drawable.irish_coffee_small, "250 ml", 11.0, 0),
            OrderCoffeeDrink("Latte", R.drawable.latte_small, "300 ml", 6.0, 0),
            OrderCoffeeDrink("Latte Macchiato", R.drawable.latte_macchiato_small, "300 ml", 7.0, 0)
        )
    )
}

@Preview
@Composable
fun cardPreview() {
    OrderCoffeeDrinkScreen()
}

@Composable
fun OrderCoffeeDrinkScreen() {
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
        VerticalScroller {
            Column {
                for (coffeeDrink in coffeeDrinks) {
                    OrderCoffeeDrinkCard(
                        orderCoffeeDrink = coffeeDrink,
                        onAddCoffeeDrink = { addCoffeeDrink(it) },
                        onRemoveCoffeeDrink = { removeCoffeeDrink(it) }
                    )
                }
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
        loadImageResource(id = orderCoffeeDrink.imageRes).resource.resource?.let {
            Box(
                modifier = LayoutHeight.Fill + LayoutWidth.Fill + ImagePainter(it).toModifier()
            )
        }
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

@Model
data class OrderCoffeeDrink(
    val name: String,
    @DrawableRes val imageRes: Int,
    val description: String,
    val price: Double,
    var count: Int = 0
)

@Model
data class OrderCoffeeDrinkData(
    val orderCoffeeDrinks: ModelList<OrderCoffeeDrink>
) {
    val totalPrice: Double = orderCoffeeDrinks.asSequence()
            .filter { it.count != 0 }
            .map { it.count * it.price }
            .sum()
}
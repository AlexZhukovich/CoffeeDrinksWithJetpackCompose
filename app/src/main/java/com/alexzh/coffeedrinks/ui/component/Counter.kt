package com.alexzh.coffeedrinks.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alexzh.coffeedrinks.data.order.OrderCoffeeDrink

@Composable
fun Counter(
    orderCoffeeDrink: OrderCoffeeDrink,
    onAdded: (Long) -> Unit,
    onRemoved: (Long) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(size = 5.dp),
        border = BorderStroke(1.dp, Color.Gray),
        color = Color.Transparent
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    modifier = Modifier.preferredWidth(40.dp),
                    colors = ButtonDefaults.outlinedButtonColors(),
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = { onRemoved(orderCoffeeDrink.id) }
                ) {
                    Text(
                        text = "—",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground
                    )
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = orderCoffeeDrink.count.toString(),
                    style = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Center),
                    color = MaterialTheme.colors.onBackground
                )
                Button(
                    modifier = Modifier.preferredWidth(40.dp),
                    colors = ButtonDefaults.outlinedButtonColors(),
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = { onAdded(orderCoffeeDrink.id) }
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

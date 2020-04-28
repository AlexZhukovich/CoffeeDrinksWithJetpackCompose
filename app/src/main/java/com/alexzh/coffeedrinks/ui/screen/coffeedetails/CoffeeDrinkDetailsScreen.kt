package com.alexzh.coffeedrinks.ui.screen.coffeedetails

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.drawOpacity
import androidx.ui.core.paint
import androidx.ui.core.tag
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ColorPainter
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.material.FloatingActionButton
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.res.imageResource
import androidx.ui.text.AnnotatedString
import androidx.ui.text.SpanStyle
import androidx.ui.text.font.FontStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextAlign
import androidx.ui.text.style.TextDecoration
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.Screen
import com.alexzh.coffeedrinks.ui.appTypography
import com.alexzh.coffeedrinks.ui.lightThemeColors
import com.alexzh.coffeedrinks.ui.navigateTo
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.mapper.CoffeeDrinkDetailMapper
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.model.CoffeeDrinkDetail

private const val SURFACE_TAG = "surface"
private const val HEADER_TAG = "header"
private const val APP_BAR_TAG = "app_bar"
private const val FAB_TAG = "fab"
private const val DRINK_NAME_TAG = "drink_name"
private const val DRINK_IMAGE_TAG = "drink_image"
private const val DRINK_DESCRIPTION_TAG = "drink_description"
private const val DRINK_INGREDIENTS_TAG = "drink_ingredients"

@Preview
@Composable
fun previewScreen() {
    val coffeeDrinkId = 1L
    val repository = RuntimeCoffeeDrinkRepository
    val mapper = CoffeeDrinkDetailMapper()
    MaterialTheme(colors = lightThemeColors, typography = appTypography) {
        CoffeeDrinkDetailsScreen(repository, mapper, coffeeDrinkId)
    }
}

@Composable
fun CoffeeDrinkDetailsScreen(
    repository: CoffeeDrinkRepository,
    mapper: CoffeeDrinkDetailMapper,
    coffeeDrinkId: Long
) {
    val coffeeDrink = mapper.map(repository.getCoffeeDrink(coffeeDrinkId))

    if (coffeeDrink == null) {
        navigateTo(Screen.CoffeeDrinks)
        return
    }

    ConstraintLayout(
        constraintSet = ConstraintSet {
            val surface = tag(SURFACE_TAG)
            val header = tag(HEADER_TAG)
            val appBar = tag(APP_BAR_TAG)
            val fab = tag(FAB_TAG)
            val name = tag(DRINK_NAME_TAG)
            val logo = tag(DRINK_IMAGE_TAG)
            val description = tag(DRINK_DESCRIPTION_TAG)
            val ingredients = tag(DRINK_INGREDIENTS_TAG)

            surface.apply {
                left constrainTo parent.left
                top constrainTo parent.top
                right constrainTo parent.right
                bottom constrainTo parent.bottom
            }

            appBar.apply {
                top constrainTo parent.top
                left constrainTo parent.left
                right constrainTo parent.right
            }

            logo.apply {
                top constrainTo parent.top
                bottom constrainTo header.bottom
                left constrainTo parent.left
                right constrainTo parent.right
            }

            name.apply {
                top constrainTo header.bottom
            }

            description.apply {
                top constrainTo name.bottom
                left constrainTo parent.left
                right constrainTo parent.right
            }

            ingredients.apply {
                top constrainTo description.bottom
                left constrainTo parent.left
                right constrainTo parent.right
            }

            fab.apply {
                top constrainTo header.bottom
                bottom constrainTo header.bottom
                right constrainTo parent.right
            }
        }
    ) {
        val coffeeDictionary = repository.getCoffeeDrinks()
            .map { it.name.toLowerCase() }
            .filter { it != coffeeDrink.name.toLowerCase() }

        val firstCoffeeDrinkPos = coffeeDrink.description
            .indexOf(string = coffeeDrink.name, ignoreCase = true)

        val includedCoffeeDrinks = mutableListOf<Substring>()
        coffeeDictionary.forEach { name ->
            var index = coffeeDrink.description.indexOf(string = name, ignoreCase = true)

            while (index > -1) {
                includedCoffeeDrinks.add(Substring(name, index))
                index = coffeeDrink.description.indexOf(
                    string = coffeeDrink.name,
                    startIndex = index + 1,
                    ignoreCase = true
                )
            }
        }

        val description = AnnotatedString {
            append(coffeeDrink.description)
            if (firstCoffeeDrinkPos > -1 ) {
                addStyle(
                    style = SpanStyle(color = Color(0xFF562a1f), fontWeight = FontWeight.Bold, fontSize = 18.sp),
                    start = firstCoffeeDrinkPos, end = firstCoffeeDrinkPos + coffeeDrink.name.length
                )
            }
            includedCoffeeDrinks.forEach {
                addStyle(
                    style = SpanStyle(fontStyle = FontStyle.Italic, textDecoration = TextDecoration.Underline),
                    start = it.startPosition, end = it.endPosition
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize() +
                    Modifier.drawBackground(color = MaterialTheme.colors.surface) +
                    Modifier.drawBackground(color = MaterialTheme.colors.surface) +
                    Modifier.tag(SURFACE_TAG)
        )
        Box(
            modifier = Modifier.tag(HEADER_TAG) +
                    Modifier.fillMaxWidth() +
                    Modifier.preferredHeight(220.dp) +
                    Modifier.paint(
                        painter = if (isSystemInDarkTheme()) {
                            ColorPainter(Color.White)
                        } else {
                            ColorPainter(MaterialTheme.colors.primary)
                        },
                        alpha = 0.95f
                    )
        )

        TopAppBar(
            title = { },
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            modifier = Modifier.tag(APP_BAR_TAG),
            navigationIcon = {
                IconButton(onClick = { navigateTo(Screen.CoffeeDrinks) }) {
                    Icon(
                        painter = ImagePainter(image = imageResource(id = R.drawable.ic_arrow_back_white)),
                        tint = if (isSystemInDarkTheme()) { Color.Black } else { MaterialTheme.colors.onPrimary }
                    )
                }
            }
        )

        FloatingActionButton(
            modifier = Modifier.padding(end = 16.dp) + Modifier.tag(FAB_TAG),
            shape = CircleShape,
            backgroundColor = MaterialTheme.colors.secondary,
            onClick = { onFavouriteStateChanged(repository, coffeeDrink) }
        ) {
            Icon(
                painter = ImagePainter(
                    imageResource(
                        if (coffeeDrink.isFavourite) {
                            R.drawable.ic_favorite_white
                        } else {
                            R.drawable.ic_favorite_border_white
                        }
                    )
                ),
                tint = MaterialTheme.colors.onSecondary
            )
        }

        Image(
            painter = ImagePainter(imageResource(id = R.drawable.americano_small)),
            modifier = Modifier.tag(DRINK_IMAGE_TAG)
        )

        Text(
            text = coffeeDrink.name,
            style = MaterialTheme.typography.h4.copy(MaterialTheme.colors.onSurface),
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp) +
                    Modifier.tag(DRINK_NAME_TAG)
        )

        Text(
//            text = description,
            text = coffeeDrink.description,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Justify
            ),
            modifier = Modifier.fillMaxWidth() +
                    Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp) +
                    Modifier.drawOpacity(0.54f) +
                    Modifier.tag(DRINK_DESCRIPTION_TAG)
        )

        Text(
            text = coffeeDrink.ingredients,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Justify
            ),
            modifier = Modifier.fillMaxWidth() +
                    Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp) +
                    Modifier.drawOpacity(0.54f) +
                    Modifier.tag(DRINK_INGREDIENTS_TAG)
        )
    }
}

private data class Substring(
    val string: String,
    val startPosition: Int
) {
    val endPosition = startPosition + string.length
}

private fun onFavouriteStateChanged(
    repository: CoffeeDrinkRepository,
    coffeeDrink: CoffeeDrinkDetail
) {
    val newFavouriteState = !coffeeDrink.isFavourite
    coffeeDrink.isFavourite = newFavouriteState

    repository.getCoffeeDrink(coffeeDrink.id)?.copy(isFavourite = newFavouriteState)?.let {
        repository.updateCoffeeDrink(it)
    }
}
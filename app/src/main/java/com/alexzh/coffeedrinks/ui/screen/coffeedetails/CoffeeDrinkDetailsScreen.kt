package com.alexzh.coffeedrinks.ui.screen.coffeedetails

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.drawOpacity
import androidx.ui.core.paint
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.drawBackground
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ColorPainter
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.Dimension
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.preferredHeight
import androidx.ui.layout.preferredSize
import androidx.ui.material.FloatingActionButton
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextAlign
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
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.mapper.CoffeeDrinkDetailMapper
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.model.CoffeeDrinkDetail
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Preview
@Composable
@ExperimentalCoroutinesApi
fun previewScreen() {
    val coffeeDrinkId = 1L
    val repository = RuntimeCoffeeDrinkRepository
    val mapper = CoffeeDrinkDetailMapper()
    val router = AppRouter()
    MaterialTheme(colors = lightThemeColors, typography = appTypography) {
        CoffeeDrinkDetailsScreen(router, repository, mapper, coffeeDrinkId)
    }
}

@Composable
fun CoffeeDrinkDetailsScreen(
    router: Router,
    repository: CoffeeDrinkRepository,
    mapper: CoffeeDrinkDetailMapper,
    coffeeDrinkId: Long
) {
    val coffeeDrink = mapper.map(repository.getCoffeeDrink(coffeeDrinkId))

    if (coffeeDrink != null) {
        CoffeeDrinkDetailsScreenUI(
            router,
            repository,
            coffeeDrink
        )
    }
}

@Composable
private fun CoffeeDrinkDetailsScreenUI(
    router: Router,
    repository: CoffeeDrinkRepository,
    coffeeDrink: CoffeeDrinkDetail
) {
    ConstraintLayout {
        val startGuideline = createGuidelineFromStart(16.dp)
        val endGuideline = createGuidelineFromEnd(16.dp)
        val (surface, header, appBar, fab, name, logo, description, ingredients) = createRefs()

        Box(modifier = Modifier.constrainAs(surface) { centerTo(parent) }
                .fillMaxSize()
                .drawBackground(color = MaterialTheme.colors.surface)
        )

        Box(modifier = Modifier.constrainAs(header) { centerHorizontallyTo(surface) }
                .fillMaxWidth()
                .preferredHeight(220.dp)
                .paint(
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
            modifier = Modifier.constrainAs(appBar) { centerHorizontallyTo(header) },
            navigationIcon = {
                IconButton(onClick = { router.navigateTo(RouterDestination.CoffeeDrinks) }) {
                    Icon(
                            painter = ImagePainter(image = imageResource(id = R.drawable.ic_arrow_back_white)),
                            tint = if (isSystemInDarkTheme()) {
                                Color.Black
                            } else {
                                MaterialTheme.colors.onPrimary
                            }
                    )
                }
            }
        )

        Image(
                modifier = Modifier.constrainAs(logo) { centerTo(header) }
                        .preferredSize(180.dp),
                painter = ImagePainter(imageResource(id = R.drawable.americano_small))
        )

        FloatingActionButton(
            modifier = Modifier.constrainAs(fab) {
                linkTo(header.bottom, header.bottom)
                bottom.linkTo(header.bottom)
                end.linkTo(endGuideline)
            },
            shape = CircleShape,
            backgroundColor = MaterialTheme.colors.secondary,
            onClick = {
                onFavouriteStateChanged(repository, coffeeDrink)
            }
        ) {
            Icon(
                painter = ImagePainter(
                    imageResource(
                        if (coffeeDrink.isFavourite.value) {
                            R.drawable.ic_favorite_white
                        } else {
                            R.drawable.ic_favorite_border_white
                        }
                    )
                ),
                tint = MaterialTheme.colors.onSecondary
            )
        }

        Text(
            text = coffeeDrink.name,
            style = MaterialTheme.typography.h4.copy(MaterialTheme.colors.onSurface),
            modifier = Modifier.constrainAs(name) {
                top.linkTo(header.bottom, margin = 16.dp)
                linkTo(startGuideline, endGuideline)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = coffeeDrink.description,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Justify
            ),
            modifier = Modifier.constrainAs(description) {
                top.linkTo(name.bottom, margin = 8.dp)
                linkTo(startGuideline, endGuideline)
                width = Dimension.fillToConstraints
            }
            .drawOpacity(0.54f)
        )

        Text(
            text = coffeeDrink.ingredients,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Justify
            ),
            modifier = Modifier.constrainAs(ingredients) {
                top.linkTo(description.bottom, margin = 8.dp)
                linkTo(startGuideline, endGuideline)
                width = Dimension.fillToConstraints
            }
            .drawOpacity(0.54f)
        )
    }
}

private fun onFavouriteStateChanged(
    repository: CoffeeDrinkRepository,
    coffeeDrink: CoffeeDrinkDetail
) {
    val newFavState = !coffeeDrink.isFavourite.value
    coffeeDrink.isFavourite.value = newFavState

    repository.updateFavouriteState(coffeeDrink.id, newFavState)
}

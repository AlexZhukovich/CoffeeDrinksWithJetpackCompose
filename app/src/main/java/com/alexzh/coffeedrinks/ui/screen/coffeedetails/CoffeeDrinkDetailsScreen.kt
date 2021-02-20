package com.alexzh.coffeedrinks.ui.screen.coffeedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.ImagePainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.ui.router.Router
import com.alexzh.coffeedrinks.ui.router.RouterDestination
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.model.CoffeeDrinkDetailState
import com.alexzh.coffeedrinks.ui.state.UiState

@Composable
fun CoffeeDrinkDetailsScreen(
    router: Router,
    viewModel: CoffeeDrinkDetailsViewModel,
    coffeeDrinkId: Long
) {
    viewModel.uiState.observeAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> ShowLoadingScreen()
            is UiState.Success -> ShowSuccessScreen(router, uiState.data, viewModel)
            is UiState.Error -> ShowErrorScreen()
        }
    }
    viewModel.loadCoffeeDrinkDetails(coffeeDrinkId)
}

@Composable
fun ShowLoadingScreen() {
    // TODO: implement it
}

@Composable
fun ShowSuccessScreen(
    router: Router,
    coffeeDrinkDetailState: CoffeeDrinkDetailState,
    viewModel: CoffeeDrinkDetailsViewModel
) {
    CoffeeDrinkDetailsScreenUI(
        router = router,
        coffeeDrinkDetailState = coffeeDrinkDetailState,
        viewModel = viewModel
    )
}

@Composable
fun ShowErrorScreen() {
    // TODO: implement it
}

@Composable
private fun CoffeeDrinkDetailsScreenUI(
    router: Router,
    coffeeDrinkDetailState: CoffeeDrinkDetailState,
    viewModel: CoffeeDrinkDetailsViewModel
) {
    ConstraintLayout {
        val startGuideline = createGuidelineFromStart(16.dp)
        val endGuideline = createGuidelineFromEnd(16.dp)
        val (surface, header, appBar, fab, name, logo, description, ingredients) = createRefs()

        Box(modifier = Modifier
            .constrainAs(surface) { centerTo(parent) }
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
        )

        Box(modifier = Modifier
            .constrainAs(header) { centerHorizontallyTo(surface) }
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
                        contentDescription = stringResource(R.string.action_back),
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
            modifier = Modifier
                .constrainAs(logo) { centerTo(header) }
                .preferredSize(180.dp),
            painter = ImagePainter(imageResource(id = R.drawable.americano_small)),
            contentDescription = null
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
                viewModel.changeFavouriteState(coffeeDrinkDetailState.coffeeDrinks)
            }
        ) {
            Icon(
                painter = ImagePainter(
                    imageResource(
                        if (coffeeDrinkDetailState.coffeeDrinks.isFavourite) {
                            R.drawable.ic_favorite_white
                        } else {
                            R.drawable.ic_favorite_border_white
                        }
                    )
                ),
                contentDescription = if (coffeeDrinkDetailState.coffeeDrinks.isFavourite) {
                    stringResource(R.string.mark_as_favorite)
                } else {
                    stringResource(R.string.unmark_as_favorite)
                },
                tint = MaterialTheme.colors.onSecondary
            )
        }

        Text(
            text = coffeeDrinkDetailState.coffeeDrinks.name,
            style = MaterialTheme.typography.h4.copy(MaterialTheme.colors.onSurface),
            modifier = Modifier.constrainAs(name) {
                top.linkTo(header.bottom, margin = 16.dp)
                linkTo(startGuideline, endGuideline)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = coffeeDrinkDetailState.coffeeDrinks.description,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Justify
            ),
            modifier = Modifier
                .constrainAs(description) {
                    top.linkTo(name.bottom, margin = 8.dp)
                    linkTo(startGuideline, endGuideline)
                    width = Dimension.fillToConstraints
                }
                .alpha(0.54f)
        )

        Text(
            text = coffeeDrinkDetailState.coffeeDrinks.ingredients,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Justify
            ),
            modifier = Modifier
                .constrainAs(ingredients) {
                    top.linkTo(description.bottom, margin = 8.dp)
                    linkTo(startGuideline, endGuideline)
                    width = Dimension.fillToConstraints
                }
                .alpha(0.54f)
        )
    }
}

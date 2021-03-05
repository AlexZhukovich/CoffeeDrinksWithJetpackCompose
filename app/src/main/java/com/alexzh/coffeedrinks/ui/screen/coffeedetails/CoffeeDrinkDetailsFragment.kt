package com.alexzh.coffeedrinks.ui.screen.coffeedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.ui.AppTheme
import com.alexzh.coffeedrinks.ui.Screen
import com.alexzh.coffeedrinks.ui.navigateToPreviousScreen
import com.alexzh.coffeedrinks.ui.state.UiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoffeeDrinkDetailsFragment : Fragment() {
    private val viewModel: CoffeeDrinkDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            id = R.id.coffeeDrinkDetailsFragment
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                AppTheme {
                    viewModel.uiState.observeAsState(initial = UiState.Loading).value.let { uiState ->
                        when (uiState) {
                            is UiState.Loading -> {
                                ShowLoadingCoffeeDrinkDetailsScreen()
                            }
                            is UiState.Success -> {
                                ShowSuccessCoffeeDrinkDetailsScreen(
                                    onBack = {
                                        this@CoffeeDrinkDetailsFragment.navigateToPreviousScreen(
                                            Screen.CoffeeDrinkDetails,
                                            Screen.CoffeeDrinks
                                        )
                                    },
                                    coffeeDrinkDetailState = uiState.data,
                                    viewModel = viewModel
                                )
                            }
                            is UiState.Error -> {
                                ShowErrorCoffeeDrinkDetailsScreen()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCoffeeDrinkDetails(arguments?.getLong("id") ?: -1)
    }
}

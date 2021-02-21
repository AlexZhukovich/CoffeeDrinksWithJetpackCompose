package com.alexzh.coffeedrinks.ui.screen.coffeedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.exception.NoCoffeeDrinkFoundException
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.mapper.CoffeeDrinkDetailMapper
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.model.CoffeeDrinkDetail
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.model.CoffeeDrinkDetailState
import com.alexzh.coffeedrinks.ui.state.UiState
import kotlinx.coroutines.launch

class CoffeeDrinkDetailsViewModel(
    private val repository: CoffeeDrinkRepository,
    private val mapper: CoffeeDrinkDetailMapper
) : ViewModel() {
    private val _uiState: MutableLiveData<UiState<CoffeeDrinkDetailState>> = MutableLiveData()
    val uiState: LiveData<UiState<CoffeeDrinkDetailState>>
        get() = _uiState

    fun loadCoffeeDrinkDetails(coffeeDrinkId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val coffeeDrink = mapper.map(
                repository.getCoffeeDrink(coffeeDrinkId)
            )
            if (coffeeDrink != null) {
                _uiState.value = UiState.Success(
                    CoffeeDrinkDetailState(
                        coffeeDrink
                    )
                )
            } else {
                _uiState.value = UiState.Error(NoCoffeeDrinkFoundException())
            }
        }
    }

    fun changeFavouriteState(coffeeDrink: CoffeeDrinkDetail) {
        viewModelScope.launch {
            repository.updateFavouriteState(coffeeDrink.id, !coffeeDrink.isFavourite)
        }
        loadCoffeeDrinkDetails(coffeeDrink.id)
    }
}

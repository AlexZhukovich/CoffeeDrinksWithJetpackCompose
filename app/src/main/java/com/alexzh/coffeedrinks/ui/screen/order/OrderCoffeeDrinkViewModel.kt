package com.alexzh.coffeedrinks.ui.screen.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.coffeedrinks.data.order.OrderCoffeeDrinksRepository
import com.alexzh.coffeedrinks.ui.screen.order.model.OrderCoffeeDrinkState
import com.alexzh.coffeedrinks.ui.state.UiState
import java.math.BigDecimal
import kotlinx.coroutines.launch

class OrderCoffeeDrinkViewModel(
    private val repository: OrderCoffeeDrinksRepository
) : ViewModel() {
    private val _uiState: MutableLiveData<UiState<OrderCoffeeDrinkState>> = MutableLiveData(UiState.Loading)
    val uiState: LiveData<UiState<OrderCoffeeDrinkState>>
        get() = _uiState

    init {
        loadDrinks()
    }

    private fun loadDrinks() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val coffeeDrinks = repository.getCoffeeDrinks()
            val totalCount = coffeeDrinks
                .filter { it.count > 0 }
                .map { it.count * it.price }
                .sum()
            _uiState.value = UiState.Success(
                OrderCoffeeDrinkState(
                    coffeeDrinks = coffeeDrinks,
                    totalPrice = BigDecimal(totalCount)
                )
            )
            repository.getCoffeeDrinks()
        }
    }

    fun addDrink(coffeeDrinkId: Long) {
        viewModelScope.launch {
            repository.add(coffeeDrinkId)
            loadDrinks()
        }
    }

    fun removeDrink(coffeeDrinkId: Long) {
        viewModelScope.launch {
            repository.remove(coffeeDrinkId)
            loadDrinks()
        }
    }
}

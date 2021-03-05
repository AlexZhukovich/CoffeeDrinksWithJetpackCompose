package com.alexzh.coffeedrinks.ui.screen.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.coffeedrinks.data.order.OrderCoffeeDrinksRepository
import com.alexzh.coffeedrinks.ui.screen.order.model.OrderCoffeeDrinkState
import com.alexzh.coffeedrinks.ui.state.UiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal

class OrderCoffeeDrinkViewModel(
    private val repository: OrderCoffeeDrinksRepository
) : ViewModel() {
    private val _uiState: MutableLiveData<UiState<OrderCoffeeDrinkState>> = MutableLiveData()
    val uiState: LiveData<UiState<OrderCoffeeDrinkState>>
        get() = _uiState

    fun loadDrinks() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getCoffeeDrinks()
                .collect { coffeeDrinks ->
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
                }
        }
    }

    fun addDrink(coffeeDrinkId: Long) {
        viewModelScope.launch {
            repository.add(coffeeDrinkId)
                .collect { isAdded ->
                    if (isAdded) {
                        loadDrinks()
                    }
                }
        }
    }

    fun removeDrink(coffeeDrinkId: Long) {
        viewModelScope.launch {
            repository.remove(coffeeDrinkId)
                .collect { isRemoved ->
                    if (isRemoved) {
                        loadDrinks()
                    }
                }
        }
    }
}

package com.alexzh.coffeedrinks.data

object RuntimeCoffeeDrinkRepository : CoffeeDrinkRepository {
    private val coffeeDrinks: MutableList<CoffeeDrink> = initCoffeeDrinks()

    override fun getCoffeeDrinks(): List<CoffeeDrink> {
        return coffeeDrinks
    }

    override fun getCoffeeDrink(id: Long): CoffeeDrink? {
        return coffeeDrinks.firstOrNull { it.id == id }
    }

    override fun updateFavouriteState(id: Long, newFavouriteState: Boolean): Boolean {
        val position = coffeeDrinks.indexOfFirst { it.id == id }
        return if (position > -1) {
            val oldCoffeeDrink = coffeeDrinks.first { it.id == id }
            val newCoffeeDrink = oldCoffeeDrink.copy(isFavourite = newFavouriteState)
            coffeeDrinks.remove(oldCoffeeDrink)
            coffeeDrinks.add(position, newCoffeeDrink)
            true
        } else {
            false
        }
    }

    // TODO: should return List<CoffeeDrink> instead of MutableList<CoffeeDrink>
    // TODO: should be used interface in constructor
    private fun initCoffeeDrinks(): MutableList<CoffeeDrink> {
        return DummyCoffeeDrinksDataSource().getCoffeeDrinks() as MutableList<CoffeeDrink>
    }
}

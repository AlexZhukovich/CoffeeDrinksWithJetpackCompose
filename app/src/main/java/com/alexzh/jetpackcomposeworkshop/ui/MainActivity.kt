package com.alexzh.jetpackcomposeworkshop.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import com.alexzh.jetpackcomposeworkshop.ui.coffeedrinks.CoffeeDrinksScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CoffeeDrinksScreen()
            }
        }
    }
}

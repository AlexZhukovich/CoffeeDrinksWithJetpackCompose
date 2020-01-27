package com.alexzh.jetpackcomposeworkshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.ui.core.Text
import androidx.ui.core.setContent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text(text = getString(R.string.app_name))
        }
    }
}

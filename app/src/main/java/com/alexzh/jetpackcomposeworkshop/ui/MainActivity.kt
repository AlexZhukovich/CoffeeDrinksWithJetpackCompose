package com.alexzh.jetpackcomposeworkshop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.ui.core.Text
import androidx.ui.core.setContent
import com.alexzh.jetpackcomposeworkshop.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text(text = getString(R.string.app_name))
        }
    }
}

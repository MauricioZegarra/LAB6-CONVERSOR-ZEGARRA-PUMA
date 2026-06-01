package com.example.conversor.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ConversionApp(controller: ConversionController) {
    MaterialTheme {
        ConversionScreen(controller)
    }
}
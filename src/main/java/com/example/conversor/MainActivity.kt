package com.example.conversor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import com.example.conversor.data.DefaultConversionRepository
import com.example.conversor.data.SharedPreferencesCustomConversionStore
import com.example.conversor.ui.ConversionController
import com.example.conversor.ui.ConversionApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val controller = ConversionController(
            DefaultConversionRepository(
                SharedPreferencesCustomConversionStore(this)
            )
        )

        setContent {
            val appController = remember { controller }
            ConversionApp(appController)
        }
    }
}
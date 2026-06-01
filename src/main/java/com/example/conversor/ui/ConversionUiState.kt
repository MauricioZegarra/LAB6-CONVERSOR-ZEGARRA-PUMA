package com.example.conversor.ui

import com.example.conversor.model.LinearConversion

data class ConversionUiState(
    val selectedConversionId: String = "",
    val inputValue: String = "",
    val outputValue: String = "",
    val message: String = "",
    val customTitle: String = "",
    val customFromUnit: String = "",
    val customToUnit: String = "",
    val customFactor: String = "",
    val customOffset: String = "",
    val customConversions: List<LinearConversion> = emptyList()
)
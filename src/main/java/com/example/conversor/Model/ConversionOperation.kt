package com.example.conversor.model

interface ConversionOperation {
    val id: String
    val title: String
    val fromUnit: String
    val toUnit: String
    fun convert(value: Double): Double
}
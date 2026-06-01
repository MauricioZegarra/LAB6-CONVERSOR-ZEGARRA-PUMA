package com.example.conversor.model

data class LinearConversion(
    override val id: String,
    override val title: String,
    override val fromUnit: String,
    override val toUnit: String,
    val factor: Double,
    val offset: Double = 0.0
) : ConversionOperation {
    override fun convert(value: Double): Double {
        return value * factor + offset
    }
}
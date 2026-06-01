package com.example.conversor.model

data class TemperatureConversion(
    override val id: String,
    override val title: String,
    override val fromUnit: String,
    override val toUnit: String
) : ConversionOperation {
    override fun convert(value: Double): Double {
        return when (id) {
            "temp_c_f" -> value * 9.0 / 5.0 + 32.0
            "temp_f_c" -> (value - 32.0) * 5.0 / 9.0
            else -> value
        }
    }
}
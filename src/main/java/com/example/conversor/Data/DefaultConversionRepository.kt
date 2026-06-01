package com.example.conversor.data

import com.example.conversor.domain.ConversionRepository
import com.example.conversor.model.ConversionOperation
import com.example.conversor.model.LinearConversion
import com.example.conversor.model.TemperatureConversion

class DefaultConversionRepository(
    private val customConversionStore: CustomConversionStore
) : ConversionRepository {

    private var customConversionsCache: MutableList<LinearConversion> =
        customConversionStore.load().toMutableList()

    private val builtInConversions: List<ConversionOperation> = listOf(
        LinearConversion("len_m_cm", "Metro a centímetro", "m", "cm", 100.0),
        LinearConversion("len_cm_m", "Centímetro a metro", "cm", "m", 0.01),
        LinearConversion("len_km_m", "Kilómetro a metro", "km", "m", 1000.0),
        LinearConversion("mass_kg_g", "Kilogramo a gramo", "kg", "g", 1000.0),
        LinearConversion("mass_g_kg", "Gramo a kilogramo", "g", "kg", 0.001),
        LinearConversion("mass_kg_lb", "Kilogramo a libra", "kg", "lb", 2.2046226218),
        LinearConversion("mass_lb_kg", "Libra a kilogramo", "lb", "kg", 0.45359237),
        TemperatureConversion("temp_c_f", "Celsius a Fahrenheit", "°C", "°F"),
        TemperatureConversion("temp_f_c", "Fahrenheit a Celsius", "°F", "°C")
    )

    override fun allConversions(): List<ConversionOperation> {
        return builtInConversions + customConversionsCache
    }

    override fun customConversions(): List<LinearConversion> {
        return customConversionsCache.toList()
    }

    override fun addCustomConversion(conversion: LinearConversion) {
        customConversionsCache = (customConversionsCache + conversion).toMutableList()
        customConversionStore.save(customConversionsCache)
    }

    override fun deleteCustomConversion(id: String) {
        customConversionsCache = customConversionsCache.filterNot { it.id == id }.toMutableList()
        customConversionStore.save(customConversionsCache)
    }

    override fun convert(conversionId: String, value: Double): Double? {
        return allConversions().firstOrNull { it.id == conversionId }?.convert(value)
    }
}
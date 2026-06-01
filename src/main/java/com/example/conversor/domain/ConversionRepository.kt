package com.example.conversor.domain

import com.example.conversor.model.ConversionOperation
import com.example.conversor.model.LinearConversion

interface ConversionRepository {
    fun allConversions(): List<ConversionOperation>
    fun customConversions(): List<LinearConversion>
    fun addCustomConversion(conversion: LinearConversion)
    fun deleteCustomConversion(id: String)
    fun convert(conversionId: String, value: Double): Double?
}
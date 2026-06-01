package com.example.conversor.data

import com.example.conversor.model.LinearConversion

interface CustomConversionStore {
    fun load(): List<LinearConversion>
    fun save(conversions: List<LinearConversion>)
}
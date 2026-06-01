package com.example.conversor.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.conversor.domain.ConversionRepository
import com.example.conversor.model.LinearConversion
import java.util.UUID

class ConversionController(
    private val repository: ConversionRepository
) {
    var uiState by mutableStateOf(
        ConversionUiState(
            selectedConversionId = repository.allConversions().firstOrNull()?.id.orEmpty(),
            customConversions = repository.customConversions()
        )
    )
        private set

    fun onInputChange(value: String) {
        uiState = uiState.copy(inputValue = value, message = "")
    }

    fun onSelectedConversionChange(id: String) {
        uiState = uiState.copy(selectedConversionId = id, message = "", outputValue = "")
    }

    fun onCustomTitleChange(value: String) {
        uiState = uiState.copy(customTitle = value, message = "")
    }

    fun onCustomFromUnitChange(value: String) {
        uiState = uiState.copy(customFromUnit = value, message = "")
    }

    fun onCustomToUnitChange(value: String) {
        uiState = uiState.copy(customToUnit = value, message = "")
    }

    fun onCustomFactorChange(value: String) {
        uiState = uiState.copy(customFactor = value, message = "")
    }

    fun onCustomOffsetChange(value: String) {
        uiState = uiState.copy(customOffset = value, message = "")
    }

    fun convert() {
        val input = uiState.inputValue.replace(',', '.').toDoubleOrNull()
        val conversionId = uiState.selectedConversionId

        if (input == null) {
            uiState = uiState.copy(message = "Ingresa un valor válido")
            return
        }

        val result = repository.convert(conversionId, input)
        if (result == null) {
            uiState = uiState.copy(message = "Selecciona una conversión válida")
            return
        }

        uiState = uiState.copy(
            outputValue = formatNumber(result),
            message = "Conversión realizada"
        )
    }

    fun saveCustomConversion() {
        val title = uiState.customTitle.trim()
        val fromUnit = uiState.customFromUnit.trim()
        val toUnit = uiState.customToUnit.trim()
        val factor = uiState.customFactor.replace(',', '.').toDoubleOrNull()
        val offset = uiState.customOffset.replace(',', '.').toDoubleOrNull() ?: 0.0

        if (title.isEmpty() || fromUnit.isEmpty() || toUnit.isEmpty() || factor == null) {
            uiState = uiState.copy(message = "Completa nombre, unidades y factor")
            return
        }

        val conversion = LinearConversion(
            id = UUID.randomUUID().toString(),
            title = title,
            fromUnit = fromUnit,
            toUnit = toUnit,
            factor = factor,
            offset = offset
        )

        repository.addCustomConversion(conversion)

        val updated = repository.customConversions()
        val selected = uiState.selectedConversionId.ifBlank { repository.allConversions().firstOrNull()?.id.orEmpty() }

        uiState = uiState.copy(
            customTitle = "",
            customFromUnit = "",
            customToUnit = "",
            customFactor = "",
            customOffset = "",
            customConversions = updated,
            selectedConversionId = selected,
            message = "Conversión personalizada guardada"
        )
    }

    fun deleteCustomConversion(id: String) {
        repository.deleteCustomConversion(id)
        uiState = uiState.copy(
            customConversions = repository.customConversions(),
            message = "Conversión eliminada"
        )
    }

    fun availableConversions() = repository.allConversions()

    private fun formatNumber(value: Double): String {
        val text = value.toString()
        return if (text.contains(".")) text.trimEnd('0').trimEnd('.') else text
    }
}
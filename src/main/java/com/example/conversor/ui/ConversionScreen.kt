package com.example.conversor.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversionScreen(controller: ConversionController) {
    val state = controller.uiState
    val conversions = controller.availableConversions()
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Conversor de unidades") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    androidx.compose.foundation.layout.Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Conversión", style = MaterialTheme.typography.titleMedium)
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = conversions.firstOrNull { it.id == state.selectedConversionId }?.title.orEmpty(),
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Tipo de conversión") },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                }
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                conversions.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text("${item.title} (${item.fromUnit} → ${item.toUnit})") },
                                        onClick = {
                                            controller.onSelectedConversionChange(item.id)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            value = state.inputValue,
                            onValueChange = controller::onInputChange,
                            label = { Text("Valor a convertir") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = controller::convert,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Convertir")
                        }

                        OutlinedTextField(
                            value = state.outputValue,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Resultado") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (state.message.isNotBlank()) {
                            Text(state.message)
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    androidx.compose.foundation.layout.Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Nueva conversión personalizada", style = MaterialTheme.typography.titleMedium)

                        OutlinedTextField(
                            value = state.customTitle,
                            onValueChange = controller::onCustomTitleChange,
                            label = { Text("Nombre") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = state.customFromUnit,
                            onValueChange = controller::onCustomFromUnitChange,
                            label = { Text("Unidad origen") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = state.customToUnit,
                            onValueChange = controller::onCustomToUnitChange,
                            label = { Text("Unidad destino") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = state.customFactor,
                            onValueChange = controller::onCustomFactorChange,
                            label = { Text("Factor") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = state.customOffset,
                            onValueChange = controller::onCustomOffsetChange,
                            label = { Text("Offset opcional") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = controller::saveCustomConversion,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Guardar conversión")
                        }
                    }
                }
            }

            item {
                Text(
                    "Conversiones guardadas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(state.customConversions, key = { it.id }) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    androidx.compose.foundation.layout.Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(item.title, style = MaterialTheme.typography.titleSmall)
                        Text("${item.fromUnit} → ${item.toUnit}")
                        Text("factor: ${item.factor}")
                        if (item.offset != 0.0) {
                            Text("offset: ${item.offset}")
                        }
                        TextButton(
                            onClick = { controller.deleteCustomConversion(item.id) }
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}
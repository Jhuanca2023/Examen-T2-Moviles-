package com.example.exament2

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person

import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListadoPersonas(
    personas: List<Persona>,
    filtro: String,
    onFiltroChange: (String) -> Unit,
    onConsultar: () -> Unit,
    onNuevo: () -> Unit,
    onEditar: (Persona) -> Unit,
    onEliminar: (Persona) -> Unit
) {
    var personaAEliminar by remember { mutableStateOf<Persona?>(null) }
    var showDialogEliminar by remember { mutableStateOf(false) }
    var personaDetalle by remember { mutableStateOf<Persona?>(null) }
    var showDetalle by remember { mutableStateOf(false) }


    AnimatedVisibility(
        visible = showDetalle && personaDetalle != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        if (personaDetalle != null) {
            AlertDialog(
                onDismissRequest = { showDetalle = false },
                title = { Text("Detalles de la Persona", fontWeight = FontWeight.Bold) },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        DetalleRow("Nombre", personaDetalle!!.nombre)
                        DetalleRow("Documento", personaDetalle!!.documento)
                        DetalleRow("Dirección", personaDetalle!!.direccion)
                        DetalleRow("Teléfono", personaDetalle!!.telefono)
                        DetalleRow("Estado Civil", personaDetalle!!.estadoCivil ?: "-")
                        DetalleRow("Distrito", personaDetalle!!.distrito ?: "-")
                        DetalleRow("Género", generoTexto(personaDetalle!!))
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showDetalle = false }) {
                        Text("Cerrar")
                    }
                }
            )
        }
    }

    if (showDialogEliminar && personaAEliminar != null) {
        AlertDialog(
            onDismissRequest = { showDialogEliminar = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Está seguro que desea eliminar a ${personaAEliminar!!.nombre}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEliminar(personaAEliminar!!)
                        showDialogEliminar = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Red
                    )
                ) { 
                    Text("Eliminar") 
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialogEliminar = false }) { 
                    Text("Cancelar") 
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = filtro,
            onValueChange = onFiltroChange,
            label = { Text("Buscar personas...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = "Buscar")
            },
            trailingIcon = {
                IconButton(onClick = onConsultar) {
                    Icon(Icons.Default.Person, contentDescription = "Consultar")
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total de personas registradas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "${personas.size}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(personas) { persona ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar persona",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(36.dp)
                                .padding(end = 12.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = persona.nombre,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = persona.documento,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(
                            onClick = {
                                personaDetalle = persona
                                showDetalle = true
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Ver detalles",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(
                            onClick = { onEditar(persona) },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Editar",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(
                            onClick = {
                                personaAEliminar = persona
                                showDialogEliminar = true
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetalleRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label + ":",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

fun generoTexto(persona: Persona): String {
    return when {
        persona.generoMasculino && persona.generoFemenino -> "Masculino y Femenino"
        persona.generoMasculino -> "Masculino"
        persona.generoFemenino -> "Femenino"
        else -> "No especificado"
    }
} 
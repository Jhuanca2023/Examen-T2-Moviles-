package com.example.exament2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.exament2.ui.theme.ExamenT2Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            var darkTheme by remember { mutableStateOf(isDarkTheme) }
            
            ExamenT2Theme(darkTheme = darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppPadronPersonas(
                        darkTheme = darkTheme,
                        onToggleTheme = { darkTheme = !darkTheme }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppPadronPersonas(
    darkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val viewModel: PersonaViewModel = viewModel()
    var pantalla by remember { mutableStateOf("listado") }
    var filtro by remember { mutableStateOf("") }
    var personasFiltradas by remember { mutableStateOf(listOf<Persona>()) }
    val personas by viewModel.personas.observeAsState(emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var personaEditando by remember { mutableStateOf<Persona?>(null) }

    LaunchedEffect(filtro, personas) {
        personasFiltradas = if (filtro.isBlank()) {
            personas
        } else {
            personas.filter { 
                it.nombre.contains(filtro, true) || 
                it.documento.contains(filtro, true) ||
                it.telefono.contains(filtro, true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Padrón de Personas",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onToggleTheme,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (darkTheme) Icons.Default.Person else Icons.Default.Person,
                            contentDescription = "Cambiar tema",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { pantalla = "registro" },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp
                )
            ) {
                Icon(
                    Icons.Default.Add, 
                    contentDescription = "Registrar Persona",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        snackbarHost = { 
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { snackbarData ->
                    Snackbar(
                        snackbarData = snackbarData,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        actionColor = MaterialTheme.colorScheme.primary
                    )
                }
            ) 
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (pantalla) {
                "listado" -> PantallaListadoPersonas(
                    personas = personasFiltradas,
                    filtro = filtro,
                    onFiltroChange = { filtro = it },
                    onConsultar = {
                        scope.launch {
                            val mensaje = if (personasFiltradas.isEmpty()) {
                                "No se encontraron registros"
                            } else {
                                "Se encontraron ${personasFiltradas.size} persona${if (personasFiltradas.size == 1) "" else "s"}"
                            }
                            snackbarHostState.showSnackbar(
                                message = mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    onNuevo = { pantalla = "registro" },
                    onEditar = {
                        personaEditando = it
                        pantalla = "editar"
                    },
                    onEliminar = { persona ->
                        viewModel.eliminar(persona)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "${persona.nombre} ha sido eliminado",
                                duration = SnackbarDuration.Short
                            )
                        }
                        filtro = ""
                    }
                )
                "registro" -> PantallaRegistroPersona(
                    onPersonaRegistrada = {
                        pantalla = "listado"
                        filtro = ""
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Persona registrada exitosamente",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    viewModel = viewModel,
                    onCancelar = { pantalla = "listado" },
                    personaEditar = null
                )
                "editar" -> PantallaRegistroPersona(
                    onPersonaRegistrada = {
                        pantalla = "listado"
                        filtro = ""
                        personaEditando = null
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Persona actualizada exitosamente",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    viewModel = viewModel,
                    onCancelar = {
                        pantalla = "listado"
                        personaEditando = null
                    },
                    personaEditar = personaEditando
                )
            }
        }
    }
}
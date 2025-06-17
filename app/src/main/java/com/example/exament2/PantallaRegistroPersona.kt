package com.example.exament2

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistroPersona(
    onPersonaRegistrada: () -> Unit,
    viewModel: PersonaViewModel,
    onCancelar: () -> Unit,
    personaEditar: Persona? = null
) {
    var nombre by remember { mutableStateOf(personaEditar?.nombre ?: "") }
    var documento by remember { mutableStateOf(personaEditar?.documento ?: "") }
    var direccion by remember { mutableStateOf(personaEditar?.direccion ?: "") }
    var telefono by remember { mutableStateOf(personaEditar?.telefono ?: "") }
    var estadoCivil by remember { mutableStateOf(personaEditar?.estadoCivil ?: "") }
    var distrito by remember { mutableStateOf(personaEditar?.distrito ?: "") }
    var generoMasculino by remember { mutableStateOf(personaEditar?.generoMasculino ?: false) }
    var generoFemenino by remember { mutableStateOf(personaEditar?.generoFemenino ?: false) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var showProgress by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorDialogMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val esEdicion = personaEditar != null


    val docError = documento.isNotBlank() && (!documento.matches(Regex("\\d{8}")))
    val telError = telefono.isNotBlank() && (!telefono.matches(Regex("\\d{9}")))
    val docErrorMsg = if (docError) "El documento debe tener 8 números" else ""
    val telErrorMsg = if (telError) "El teléfono debe tener 9 números" else ""


    val ubigeoPeru = mapOf(
        "La Libertad" to mapOf(
            "Trujillo" to listOf("Trujillo Centro", "El Porvenir", "Florencia de Mora", "La Esperanza", "Víctor Larco", "Huanchaco", "Moche", "Salaverry", "Laredo", "Poroto", "Simbal", "Alto Trujillo"),
            "Santiago de Chuco" to listOf("Santiago de Chuco", "Angasmarca", "Cachicadán", "Mollebamba", "Santa Cruz de Chuca", "Sitabamba", "Quiruvilca", "Mollepata", "Marcabalito"),
            "Otuzco" to listOf("Otuzco", "Agallpampa", "Charat", "Huaranchal", "La Cuesta", "Mache", "Paranday", "Salpo", "Sinsicap", "Usquil"),
            "Virú" to listOf("Virú", "Chao", "Guadalupito"),
            "Ascope" to listOf("Ascope", "Chicama", "Chocope", "Magdalena de Cao", "Paiján", "Rázuri", "Santiago de Cao", "Casa Grande"),
            "Pacasmayo" to listOf("Pacasmayo", "San Pedro de Lloc", "Guadalupe", "Jequetepeque", "San José"),
            "Chepén" to listOf("Chepén", "Pacanga", "Pueblo Nuevo"),
            "Gran Chimú" to listOf("Cascas", "Lucma", "Marmot", "Sayapullo"),
            "Julcán" to listOf("Julcán", "Calamarca", "Carabamba", "Huaso"),
            "Bolívar" to listOf("Bolívar", "Bambamarca", "Condormarca", "Longotea", "Ucuncha"),
            "Pataz" to listOf("Tayabamba", "Buldibuyo", "Chillia", "Huancaspata", "Huaylillas", "Huayo", "Ongón", "Parcoy", "Pataz", "Pías", "Santiago de Challas"),
            "Sánchez Carrión" to listOf("Huamachuco", "Curgos", "Chugay", "Cochabamba", "Marcabal", "Sanagorán", "Sartimbamba"),
            "Virú" to listOf("Virú", "Chao", "Guadalupito")
        ),
        "Lima" to mapOf(
            "Lima" to listOf("Miraflores", "San Isidro", "Surco", "La Molina", "Barranco", "San Borja", "San Miguel", "Pueblo Libre", "Chorrillos", "Ate", "Comas", "Villa El Salvador", "Villa María del Triunfo", "San Juan de Lurigancho", "San Juan de Miraflores", "Los Olivos", "Independencia", "Breña", "Lince", "Magdalena del Mar", "Jesús María", "Rímac", "San Luis", "San Martín de Porres", "Santa Anita", "El Agustino", "La Victoria", "Pucusana", "Punta Hermosa", "Punta Negra", "San Bartolo", "Santa María del Mar", "Lurín", "Chaclacayo", "Cieneguilla", "Carabayllo", "Puente Piedra", "Ancón", "Santa Rosa"),
            "Barranca" to listOf("Barranca", "Paramonga", "Pativilca", "Supe"),
            "Cajatambo" to listOf("Cajatambo", "Gorgor", "Huancapón", "Manas"),
            "Canta" to listOf("Canta", "Arahuay", "Huamantanga", "San Buenaventura"),
            "Cañete" to listOf("San Vicente de Cañete", "Asia", "Lunahuaná", "Imperial"),
            "Huaral" to listOf("Huaral", "Aucallama", "Chancay", "Atavillos Alto"),
            "Huarochirí" to listOf("Matucana", "San Mateo", "San Damián", "Antioquía"),
            "Huaura" to listOf("Huacho", "Ambar", "Caleta de Carquín", "Sayán"),
            "Oyón" to listOf("Oyón", "Andajes", "Cochamarca", "Naván"),
            "Yauyos" to listOf("Yauyos", "Huancaya", "Laraos", "Tomas")
        ),
        "Arequipa" to mapOf(
            "Arequipa" to listOf("Cercado", "Yanahuara", "Cayma", "Miraflores (Arequipa)", "Paucarpata", "Cerro Colorado"),
            "Camana" to listOf("Camana", "Mariscal Cáceres", "Nicolás de Piérola")
        ),
        "Piura" to mapOf(
            "Piura" to listOf("Piura", "Castilla", "Catacaos", "Veintiséis de Octubre"),
            "Sullana" to listOf("Sullana", "Bellavista", "Marcavelica")
        ),
        "Tacna" to mapOf(
            "Tacna" to listOf("Tacna", "Alto de la Alianza", "Ciudad Nueva", "Gregorio Albarracín"),
            "Tarata" to listOf("Tarata", "Estique", "Tarucachi")
        )
    )

    var departamento by remember { mutableStateOf("") }
    var provincia by remember { mutableStateOf("") }

    val provinciasDisponibles = if (departamento.isNotBlank()) ubigeoPeru[departamento]?.keys?.toList() ?: emptyList() else emptyList()
    val distritosDisponibles = if (departamento.isNotBlank() && provincia.isNotBlank()) {
        ubigeoPeru[departamento]?.get(provincia) ?: emptyList()
    } else {
        emptyList()
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmación") },
            text = { Text(dialogMessage) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    showProgress = true
                }) { Text("Aceptar") }
            }
        )
    }
    
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error de Validación") },
            text = { Text(errorDialogMessage) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) { Text("Entendido") }
            }
        )
    }
    
    if (showProgress) {
        LaunchedEffect(Unit) {
            delay(2000)
            showProgress = false
            onPersonaRegistrada()
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (esEdicion) "Actualizando persona..." else "Registrando persona...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        if (esEdicion) "Editar Persona" else "Registrar Nueva Persona",
                        fontWeight = FontWeight.Bold
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onCancelar) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Información Personal",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre Completo *") },
                        placeholder = { Text("Ingrese el nombre completo") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Género",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = generoMasculino,
                            onClick = {
                                generoMasculino = true
                                generoFemenino = false
                            },
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                        )
                        Text("Masculino", modifier = Modifier.padding(end = 16.dp))
                        RadioButton(
                            selected = generoFemenino,
                            onClick = {
                                generoMasculino = false
                                generoFemenino = true
                            },
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                        )
                        Text("Femenino")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = documento,
                        onValueChange = { if (it.length <= 8 && it.all { c -> c.isDigit() }) documento = it },
                        label = { Text("Documento de Identidad *") },
                        placeholder = { Text("Ingrese el número de DNI") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        enabled = !esEdicion,
                        isError = docError,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, 
                            imeAction = ImeAction.Next
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (docError) Color.Red else MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = if (docError) Color.Red else MaterialTheme.colorScheme.outline
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        }
                    )
                    AnimatedVisibility(visible = docError, enter = fadeIn(), exit = fadeOut()) {
                        Text(docErrorMsg, color = Color.Red, style = MaterialTheme.typography.labelSmall)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        label = { Text("Dirección *") },
                        placeholder = { Text("Ingrese la dirección") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { if (it.length <= 9 && it.all { c -> c.isDigit() }) telefono = it },
                        label = { Text("Teléfono *") },
                        placeholder = { Text("Ingrese el número de teléfono") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = telError,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone, 
                            imeAction = ImeAction.Next
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (telError) Color.Red else MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = if (telError) Color.Red else MaterialTheme.colorScheme.outline
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        }
                    )
                    AnimatedVisibility(visible = telError, enter = fadeIn(), exit = fadeOut()) {
                        Text(telErrorMsg, color = Color.Red, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
            

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Información Adicional",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    Text(
                        text = "Estado Civil",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    val estados = listOf("Soltero(a)", "Casado(a)", "Divorciado(a)", "Viudo(a)")
                    estados.forEach { estado ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            RadioButton(
                                selected = estadoCivil == estado,
                                onClick = { estadoCivil = estado },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary
                                )
                            )
                            Text(
                                estado,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Ubicación",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    

                    var expandedDep by remember { mutableStateOf(false) }
                    OutlinedTextField(
                        value = departamento,
                        onValueChange = {},
                        label = { Text("Departamento") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedDep = true },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expandedDep = !expandedDep }) {
                                Icon(
                                    imageVector = if (expandedDep) Icons.Filled.ArrowBack else Icons.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    DropdownMenu(
                        expanded = expandedDep,
                        onDismissRequest = { expandedDep = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ubigeoPeru.keys.forEach { dep ->
                            DropdownMenuItem(
                                text = { Text(dep) },
                                onClick = {
                                    departamento = dep
                                    provincia = ""
                                    distrito = ""
                                    expandedDep = false
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    var expandedProv by remember { mutableStateOf(false) }
                    OutlinedTextField(
                        value = provincia,
                        onValueChange = {},
                        label = { Text("Provincia") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { if (departamento.isNotBlank()) expandedProv = true },
                        readOnly = true,
                        enabled = departamento.isNotBlank(),
                        trailingIcon = {
                            IconButton(onClick = { if (departamento.isNotBlank()) expandedProv = !expandedProv }) {
                                Icon(
                                    imageVector = if (expandedProv) Icons.Filled.ArrowBack else Icons.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    DropdownMenu(
                        expanded = expandedProv,
                        onDismissRequest = { expandedProv = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        provinciasDisponibles.forEach { prov ->
                            DropdownMenuItem(
                                text = { Text(prov) },
                                onClick = {
                                    provincia = prov
                                    distrito = ""
                                    expandedProv = false
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    var expandedDist by remember { mutableStateOf(false) }
                    OutlinedTextField(
                        value = distrito,
                        onValueChange = {},
                        label = { Text("Distrito") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { if (provincia.isNotBlank()) expandedDist = true },
                        readOnly = true,
                        enabled = provincia.isNotBlank(),
                        trailingIcon = {
                            IconButton(onClick = { if (provincia.isNotBlank()) expandedDist = !expandedDist }) {
                                Icon(
                                    imageVector = if (expandedDist) Icons.Filled.ArrowBack else Icons.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    DropdownMenu(
                        expanded = expandedDist,
                        onDismissRequest = { expandedDist = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        distritosDisponibles.forEach { dist ->
                            DropdownMenuItem(
                                text = { Text(dist) },
                                onClick = {
                                    distrito = dist
                                    expandedDist = false
                                }
                            )
                        }
                    }
                }
            }
            

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onCancelar,
                    modifier = Modifier.weight(1f)
                ) { 
                    Text("Cancelar") 
                }
                
                Button(
                    onClick = {
                        val camposFaltantes = mutableListOf<String>()
                        if (nombre.isBlank()) camposFaltantes.add("Nombre Completo")
                        if (documento.isBlank()) camposFaltantes.add("Documento de Identidad")
                        if (direccion.isBlank()) camposFaltantes.add("Dirección")
                        if (telefono.isBlank()) camposFaltantes.add("Teléfono")
                        if (docError) camposFaltantes.add("Documento de Identidad válido (8 números)")
                        if (telError) camposFaltantes.add("Teléfono válido (9 números)")
                        
                        if (camposFaltantes.isNotEmpty()) {
                            errorDialogMessage = "Por favor complete los siguientes campos obligatorios:\n\n${camposFaltantes.joinToString("\n")}"
                            showErrorDialog = true
                            return@Button
                        }
                        
                        val persona = Persona(
                            documento = documento,
                            nombre = nombre,
                            direccion = direccion,
                            telefono = telefono,
                            estadoCivil = estadoCivil,
                            distrito = distrito,
                            generoMasculino = generoMasculino,
                            generoFemenino = generoFemenino
                        )
                        
                        if (esEdicion) {
                            viewModel.actualizar(persona) { exito, mensaje ->
                                if (exito) {
                                    dialogMessage = "Persona actualizada correctamente."
                                    showDialog = true
                                } else {
                                    errorDialogMessage = mensaje
                                    showErrorDialog = true
                                }
                            }
                        } else {
                            viewModel.insertar(persona) { exito, mensaje ->
                                if (exito) {
                                    dialogMessage = "¿Desea registrar a la persona?"
                                    showDialog = true
                                } else {
                                    errorDialogMessage = mensaje
                                    showErrorDialog = true
                                }
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) { 
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (esEdicion) "Actualizar" else "Registrar")
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
} 
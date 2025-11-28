package com.example.login001v.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.login001v.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    total: Int      // Total que viene desde el carrito
) {
    // Estados para guardar lo que escribe el usuario en los campos
    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var metodoPago by remember { mutableStateOf("Tarjeta de crédito") }
    var expanded by remember { mutableStateOf(false) }   // Controla si se abre o cierra el menú de pago

    Scaffold(
        // Barra superior con título y botón de volver
        topBar = {
            TopAppBar(
                title = { Text("Datos de envío y pago") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        // Box para dejar fondo bonito
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Imagen de carta difuminada como fondo
            Image(
                painter = painterResource(id = R.drawable.charmanderdestruccionabrazadora),
                contentDescription = null, // Es decorativa
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .blur(20.dp),            // imange difuminada
                alpha = 0.18f               // Transferencia
            )

            // Surface semitransparente para que el formulario se lea bien
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                shape = MaterialTheme.shapes.medium
            ) {
                // Columna principal con los campos del formulario
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // Muestra el total que viene desde el carrito
                    Text(
                        text = "Total a pagar: $$total",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    // Campo nombre completo
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre completo") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo dirección de envío
                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        label = { Text("Dirección de envío") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo ciudad / comuna
                    OutlinedTextField(
                        value = ciudad,
                        onValueChange = { ciudad = it },
                        label = { Text("Ciudad / Comuna") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo teléfono de contacto
                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { telefono = it },
                        label = { Text("Teléfono de contacto") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Título para el bloque de método de pago
                    Text(
                        text = "Método de pago",
                        fontWeight = FontWeight.SemiBold
                    )

                    // Combo simple (dropdown) para elegir método de pago
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = metodoPago,
                            onValueChange = {},           // Es solo lectura
                            readOnly = true,
                            label = { Text("Método de pago") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listOf(
                                "Tarjeta de crédito",
                                "Tarjeta de débito",
                                "Transferencia",
                                "Efectivo"
                            ).forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        metodoPago = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón para confirmar la compra
                    Button(
                        onClick = {
                            // Aquí se podrían validar datos o guardar en BD.
                            // Por ahora solo volvemos al menú principal con el nombre ingresado.
                            navController.navigate("DrawerMenu/$nombre") {
                                popUpTo("login") { inclusive = false }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Text(
                            text = "Confirmar compra",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


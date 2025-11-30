package com.example.login001v.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.login001v.data.model.CartItem // Importar el modelo CartItem
import com.example.login001v.viewmodel.CartViewModel // Importar el ViewModel del carrito

@OptIn(ExperimentalMaterial3Api::class)
// @OptIn permite el uso de APIs marcadas como "experimentales" en Material 3
@Composable
fun ProductoFormScreen(
    navController: NavController,
    nombre: String,
    precio: String,
    imgRes: Int,
    cartViewModel: CartViewModel

) {
    // Convertir precio a Int
    val precioUnitario = precio.toIntOrNull() ?: 0

    // 1. Observamos el estado del carrito para este producto
    val cartItems by cartViewModel.cartItems.collectAsState()
    // Buscamos si el producto actual ya está en el carrito
    val currentCartItem = cartItems.find { it.nombre == nombre }

    // 2. Inicializamos los estados de cantidad y certificación con los valores del carrito, si existen
    var cantidad by remember { mutableStateOf(currentCartItem?.cantidad ?: 1) }
    var conCertificacion by remember { mutableStateOf(currentCartItem?.conCertificacion ?: false) }

    // 3. Calculamos el subtotal y total basado en los estados
    val subtotal = precioUnitario * cantidad
    val total = subtotal + if (conCertificacion) 30000 else 0


    // Lógica para elevar la Card (solo visual)
    val shadowElevation by animateDpAsState(
        targetValue = if (conCertificacion) 8.dp else 2.dp,
        label = "shadowElevationAnimation"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(nombre) }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // INICIO IMAGEN Y DESCRIPCIÓN
                item {
                    // Card con sombra que depende de conCertificacion
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(shadowElevation) // Sombra dinámica
                            .clickable { conCertificacion = !conCertificacion }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = imgRes),
                                contentDescription = "Imagen de $nombre",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = nombre,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Precio Unitario: $$precio",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                // FIN IMAGEN Y DESCRIPCIÓN

                // INICIO SELECCIÓN DE CANTIDAD
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Cantidad:", style = MaterialTheme.typography.titleMedium)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Botón de Restar
                            Button(
                                onClick = { if (cantidad > 1) cantidad-- },
                                enabled = cantidad > 1,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                )
                            ) {
                                Text("-", color = MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = cantidad.toString(),
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            // Botón de Sumar
                            Button(
                                onClick = { cantidad++ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                )
                            ) {
                                Text("+", color = MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                        }
                    }
                }
                // FIN SELECCIÓN DE CANTIDAD

                // INICIO SWITCH CERTIFICACIÓN
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { conCertificacion = !conCertificacion }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                "Certificación de Originalidad",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                "Costo Adicional: $30.000 (Incluye análisis experto)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = conCertificacion,
                            onCheckedChange = { conCertificacion = it }
                        )
                    }
                    Divider()
                }
                // FIN SWITCH CERTIFICACIÓN

                // INICIO SUBTOTAL Y TOTAL
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Subtotal:", style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = "$${subtotal}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total:", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
                            Text(
                                text = "$${total}",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                // FIN TOTAL

                // 4. INICIO BOTÓN AÑADIR/ACTUALIZAR CARRITO
                item {

                    val itemToAdd = CartItem(
                        nombre = nombre,
                        precioUnitario = precioUnitario,
                        imagenRes = imgRes,
                        cantidad = cantidad,
                        conCertificacion = conCertificacion
                    )

                    Button(
                        onClick = {
                            cartViewModel.addItem(itemToAdd)
                            navController.navigate("cart_route")  //va al CarritoScreen
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        val buttonText = if (currentCartItem != null) {
                            "Actualizar Carrito (${cantidad} unidades)"
                        } else {
                            "Añadir al Carrito (${cantidad} unidades)"
                        }
                        Text(buttonText, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
                // FIN BOTÓN AÑADIR/ACTUALIZAR

                // 5. INICIO BOTÓN ELIMINAR DEL CARRITO (Solo visible si ya está en el carrito)
                if (currentCartItem != null) {
                    item {
                        OutlinedButton(
                            onClick = {
                                // Lógica para eliminar el producto del carrito
                                cartViewModel.removeItem(currentCartItem)
                                // Opcional: Volver a la pantalla anterior después de eliminar
                                navController.popBackStack()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .padding(vertical = 4.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error // Color rojo para acción de eliminar
                            ),
                            border = BorderStroke(
                                width = 1.dp, // Grosor estándar para OutlinedButton
                                color = MaterialTheme.colorScheme.error // El color de la línea del borde
                            )
                        ) {
                            Text("Eliminar del Carrito")
                        }
                    }
                }
                // FIN BOTÓN ELIMINAR

                // INICIO BOTÓN VOLVER
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        OutlinedButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(48.dp)
                        ) {
                            Text("Volver")
                        }
                    }
                }
                // FIN BOTÓN VOLVER
            }
        }
    )
}
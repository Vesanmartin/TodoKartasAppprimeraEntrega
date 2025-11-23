package com.example.login001v.view

import androidx.compose.animation.core.animateDpAsState
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
import com.example.login001v.R


@OptIn(ExperimentalMaterial3Api::class)
// @OptIn permite el uso de APIs marcadas como "experimentales" en Material 3 (la biblioteca de diseño de Android ejemplo BottomAppBar).
@Composable
fun ProductoFormScreen(
    navController: NavController,
    nombre: String,
    precio: String,
    imgRes: Int
) {
    // Convertir precio a Int
    val precioUnitario = precio.toIntOrNull() ?: 0

    // Estados
    var cantidad by remember { mutableStateOf(1) }
    var conCertificacion by remember { mutableStateOf(false) }

    // Calcular total
    val subtotal = precioUnitario * cantidad
    val total = subtotal + if (conCertificacion) 30000 else 0

    val drawableId = if (imgRes != 0) imgRes else R.drawable.charmanderdestruccionabrazadora

    Scaffold(
        bottomBar = {
            // INICIO BOTTOM APP BAR
            BottomAppBar {
                Text(
                    text = "Tienda TodoKartas",
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            // FIN BOTTOM APP BAR
        }
    ) { innerPadding ->

        // INICIO CONTENIDO PRINCIPAL
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            // INICIO IMAGEN GRANDE (con animación y sin recortes)
            item {
                var expanded by remember { mutableStateOf(false) }
                val imageHeight by animateDpAsState(
                    targetValue = if (expanded) 400.dp else 320.dp,
                    label = "imageHeight"
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageHeight)
                        .padding(vertical = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = drawableId),
                        contentDescription = nombre,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { expanded = !expanded }
                            .padding(8.dp)
                            .shadow(if (expanded) 16.dp else 6.dp, shape = MaterialTheme.shapes.medium),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(Modifier.height(12.dp))
                Text(text = nombre, style = MaterialTheme.typography.headlineSmall)
                Text(text = "Precio unitario: $${precio}", style = MaterialTheme.typography.titleMedium)
            }
            // FIN IMAGEN GRANDE

            // INICIO CONTADOR CON ETIQUETA
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Cantidad", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Subtotal a la izquierda
                            Text(
                                text = "$${subtotal}",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.primary
                            )

                            // Controles +/-
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                OutlinedButton(
                                    onClick = { if (cantidad > 1) cantidad-- },
                                    modifier = Modifier.size(40.dp)
                                ) { Text("-") }

                                Spacer(Modifier.width(12.dp))
                                Text(cantidad.toString(), style = MaterialTheme.typography.headlineMedium)
                                Spacer(Modifier.width(12.dp))

                                OutlinedButton(
                                    onClick = { cantidad++ },
                                    modifier = Modifier.size(40.dp)
                                ) { Text("+") }
                            }
                        }
                    }
                }
            }
            // FIN CONTADOR CON ETIQUETA

            // INICIO CERTIFICACIÓN PSA
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Certificación PSA", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                            Text("+ $30.000", style = MaterialTheme.typography.titleMedium)
                        }

                        Spacer(Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Botón "No"
                            Button(
                                onClick = { conCertificacion = false },
                                colors = if (!conCertificacion)
                                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                else
                                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("No", color = if (!conCertificacion) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                            }

                            Spacer(Modifier.width(8.dp))

                            // Botón "Sí"
                            Button(
                                onClick = { conCertificacion = true },
                                colors = if (conCertificacion)
                                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                else
                                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Sí", color = if (conCertificacion) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
            // FIN CERTIFICACIÓN PSA

            // INICIO TOTAL
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5))
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Total", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "$${total}",
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            // FIN TOTAL

            // INICIO BOTÓN CONFIRMAR
            item {
                Button(
                    onClick = {
                        // Aquí iría la lógica de confirmación
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Confirmar Pedido", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            // FIN BOTÓN CONFIRMAR

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
        // FIN CONTENIDO PRINCIPAL
    }
}
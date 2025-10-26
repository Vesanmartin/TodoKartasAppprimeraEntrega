package com.example.login001v.view
import com.example.login001v.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.login001v.data.model.Producto
import com.example.login001v.viewmodel.ProductoViewModel
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormScreen(
    navController: NavController,
    nombre: String,
    precio: String
) {
    // Estados locales del formulario
    var cantidad by remember { mutableStateOf(TextFieldValue("")) }
    var direccion by remember { mutableStateOf(TextFieldValue("")) }
    var eEspecial by remember { mutableStateOf(false) }
    var aPuntos by remember { mutableStateOf(false) }

    // ViewModel (inyección por defecto)
    val viewModel: ProductoViewModel = viewModel()
    val productos: List<Producto> by viewModel.productos.collectAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Text(
                    text = "Tienda TodOKartas",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen genérica del producto: Charmander para presentacion
            // Estado para controlar si está ampliada o no
            var expanded by remember { mutableStateOf(false) }

            // Animación suave entre los tamaños
            val imageHeight by animateDpAsState(
                targetValue = if (expanded) 420.dp else 250.dp,
                label = "imageHeightAnimation"
            )

            Image(
                painter = painterResource(id = R.drawable.charmanderdestruccionabrazadora),
                contentDescription = "Charmander Destrucción Abrazadora",
                modifier = Modifier
                    .height(imageHeight) // Tamaño animado
                    .fillMaxWidth()
                    .clickable { expanded = !expanded } // Toca para agrandar o reducir
                    .padding(4.dp)
                    .shadow(if (expanded) 8.dp else 2.dp, shape = MaterialTheme.shapes.medium), // sombra suave
                alignment = Alignment.Center,
                contentScale = if (expanded) ContentScale.Crop else ContentScale.Fit // cambia cómo se muestra
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = nombre, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Precio: $precio", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = eEspecial,
                    onCheckedChange = { eEspecial = it }
                )
                Text("Agregar edición especial")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = aPuntos,
                    onCheckedChange = { aPuntos = it }
                )
                Text("Agregar puntos")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val producto = Producto(
                        nombre = nombre,
                        precio = precio,
                        cantidad = cantidad.text,
                        direccion = direccion.text,
                        edicionEspecial = eEspecial,
                        agregarPuntos = aPuntos
                    )
                    viewModel.guardarProducto(producto)
                },
                enabled = cantidad.text.isNotBlank() && direccion.text.isNotBlank()
            ) {
                Text("Confirmar Pedido")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Pedidos realizados:",
                style = MaterialTheme.typography.headlineSmall
            )

            if (productos.isNotEmpty()) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(productos) { producto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = "${producto.nombre} - ${producto.precio}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "Cantidad: ${producto.cantidad}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Dirección: ${producto.direccion}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "No hay pedidos realizados",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductoFormScreen() {
    ProductoFormScreen(
        navController = rememberNavController(),
        nombre = "Producto Ejemplo",
        precio = "$10.00"
    )
}

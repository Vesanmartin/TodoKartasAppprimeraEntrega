package com.example.login001v.view

import com.example.login001v.R
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.login001v.data.model.Producto
import com.example.login001v.viewmodel.ProductoViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormScreen(
    navController: NavController,
    nombre: String,
    precio: String,
    imgRes: Int
) {
    // ESTADOS DEL FORM
    var cantidad by remember { mutableStateOf(TextFieldValue("")) }
    var direccion by remember { mutableStateOf(TextFieldValue("")) }
    var eEspecial by remember { mutableStateOf(false) }
    var aPuntos by remember { mutableStateOf(false) }

    // Si editing != null, estamos actualizando ese producto (modo edición)
    var editing by remember { mutableStateOf<Producto?>(null) }

    // Evitar crash en Preview (no existe Application)
    val isPreview = LocalInspectionMode.current

    // ViewModel + lista observada
    val vm: ProductoViewModel? = if (!isPreview) viewModel() else null
    val productos: List<Producto> =
        if (!isPreview) vm!!.productos.collectAsState(initial = emptyList()).value else emptyList()

    // Snackbar para mensajes
    val scope = rememberCoroutineScope()
    val snackHost = remember { SnackbarHostState() }

    // Imagen por defecto
    val drawableId = if (imgRes != 0) imgRes else R.drawable.charmanderdestruccionabrazadora

    // Estado de scroll de la lista , asi agregamoas
    val listState = rememberLazyListState()

    // Cuando cambie el tamaño de la lista, baja a la ultima carta agregada
    LaunchedEffect(productos.size) {
        if (productos.isNotEmpty()) {
            listState.animateScrollToItem(productos.lastIndex)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackHost) },
        bottomBar = {
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
        }
    ) { innerPadding ->

        // Contenido en una sola lista(espacio)

        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                start = 16.dp, end = 16.dp, top = 12.dp,
                bottom = 96.dp // espacio para que la BottomAppBar no tape el final
            )
        ) {

            // imagen+titulo
            item {
                // Animación simple para agrandar - achicar imagen al tocar
                var expanded by remember { mutableStateOf(false) }
                val imageHeight by animateDpAsState(
                    targetValue = if (expanded) 220.dp else 160.dp,
                    label = "imageHeight"
                )

                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = nombre,
                    modifier = Modifier
                        .height(imageHeight)
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                        .padding(4.dp)
                        .shadow(if (expanded) 8.dp else 2.dp, shape = MaterialTheme.shapes.medium),
                    alignment = Alignment.Center,
                    contentScale = if (expanded) ContentScale.Crop else ContentScale.Fit
                )

                Spacer(Modifier.height(8.dp))
                Text(text = nombre, style = MaterialTheme.typography.titleMedium)
                Text(text = "Precio: $precio", style = MaterialTheme.typography.bodyMedium)
            }

            // Formulario
            item {
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = eEspecial, onCheckedChange = { eEspecial = it })
                    Text("Agregar edición especial")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = aPuntos, onCheckedChange = { aPuntos = it })
                    Text("Agregar puntos")
                }

                Spacer(Modifier.height(12.dp))

                // CREATE / UPDATE (según editing == null)
                Button(
                    onClick = {
                        if (vm != null) {
                            if (editing == null) {
                                // CREATE
                                val producto = Producto(
                                    nombre = nombre,
                                    precio = precio,
                                    cantidad = cantidad.text,
                                    direccion = direccion.text,
                                    edicionEspecial = eEspecial,
                                    agregarPuntos = aPuntos
                                )
                                vm.guardarProducto(producto)
                                scope.launch { snackHost.showSnackbar("Pedido agregado") }
                            } else {
                                // UPDATE (copiamos el existente manteniendo el id)
                                val actualizado = editing!!.copy(
                                    cantidad = cantidad.text,
                                    direccion = direccion.text,
                                    edicionEspecial = eEspecial,
                                    agregarPuntos = aPuntos
                                )
                                vm.actualizarProducto(actualizado)
                                scope.launch { snackHost.showSnackbar("Pedido actualizado") }
                                editing = null
                            }

                            // Limpiar formulario
                            cantidad = TextFieldValue("")
                            direccion = TextFieldValue("")
                            eEspecial = false
                            aPuntos = false
                        }
                    },
                    enabled = cantidad.text.isNotBlank() && direccion.text.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (editing == null) "Confirmar Pedido" else "Actualizar Pedido")
                }

                // Cancelar modo edición
                if (editing != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        TextButton(
                            onClick = {
                                editing = null
                                cantidad = TextFieldValue("")
                                direccion = TextFieldValue("")
                                eEspecial = false
                                aPuntos = false
                            }
                        ) {
                            Text("Cancelar edición")
                        }
                    }
                }
            }

            // ----- Título de la lista -----
            item {
                Text(
                    text = "Pedidos realizados:",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // Lista de pedidos: CRUD READ
            if (productos.isNotEmpty()) {
                items(productos, key = { it.id }) { p ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(10.dp)) {
                            Text("${p.nombre} — ${p.precio}", style = MaterialTheme.typography.bodyLarge)
                            Text("Cantidad: ${p.cantidad}", style = MaterialTheme.typography.bodyMedium)
                            Text("Dirección: ${p.direccion}", style = MaterialTheme.typography.bodyMedium)

                            Spacer(Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // EDITAR: carga datos al form
                                OutlinedButton(onClick = {
                                    editing = p
                                    cantidad = TextFieldValue(p.cantidad)
                                    direccion = TextFieldValue(p.direccion)
                                    eEspecial = p.edicionEspecial
                                    aPuntos = p.agregarPuntos
                                }) { Text("Editar") }

                                // ELIMINAR con confirmación
                                var showConfirm by remember { mutableStateOf(false) }
                                OutlinedButton(
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = MaterialTheme.colorScheme.error
                                    ),
                                    onClick = { showConfirm = true }
                                ) { Text("Eliminar") }

                                if (showConfirm) {
                                    AlertDialog(
                                        onDismissRequest = { showConfirm = false },
                                        title = { Text("Eliminar pedido") },
                                        text = { Text("¿Seguro que deseas eliminar este pedido?") },
                                        confirmButton = {
                                            TextButton(onClick = {
                                                vm?.eliminarProducto(p)
                                                showConfirm = false
                                                // Si estabas editando este, limpia el form
                                                if (editing?.id == p.id) {
                                                    editing = null
                                                    cantidad = TextFieldValue("")
                                                    direccion = TextFieldValue("")
                                                    eEspecial = false
                                                    aPuntos = false
                                                }
                                                scope.launch { snackHost.showSnackbar("Pedido eliminado") }
                                            }) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
                                        },
                                        dismissButton = {
                                            TextButton(onClick = { showConfirm = false }) {
                                                Text("Cancelar")
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                // Sin pedidos: mensaje
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No realizaste un pedido", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            // ----- Botón Volver -----
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(48.dp)
                    ) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductoFormScreen() {
    val navController = rememberNavController()
    ProductoFormScreen(
        navController = navController,
        nombre = "Charmander destrucción Abrazadora",
        precio = "15990",
        imgRes = R.drawable.charmanderdestruccionabrazadora
    )
}// fin productoformscreen

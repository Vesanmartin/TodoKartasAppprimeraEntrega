package com.example.login001v.view


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.login001v.data.model.CartItem
import com.example.login001v.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val items by cartViewModel.cartItems.collectAsState()
    val subtotal by cartViewModel.subtotal.collectAsState()
    val total by cartViewModel.total.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito de Compras") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            if (items.isNotEmpty()) {
                CartBottomBar(subtotal = subtotal, total = total, onConfirm = {
                    // Lógica para finalizar la compra / pasar a pago
                    // Por ahora, solo vacía el carrito
                    cartViewModel.clearCart()
                    navController.popBackStack()
                })
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (items.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Tu carrito está vacío.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(items, key = { it.nombre }) { item ->
                        CartItemRow(
                            item = item,
                            onRemove = { cartViewModel.removeItem(item) },
                            onQuantityChange = { newQty -> cartViewModel.updateQuantity(item, newQty) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onRemove: () -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Información del Producto
        Column(modifier = Modifier.weight(1f)) {
            Text(item.nombre, style = MaterialTheme.typography.titleMedium, maxLines = 1)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                "Precio: $${item.precioUnitario}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                "Subtotal: $${item.subtotalItem}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }

        // Control de Cantidad
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.width(IntrinsicSize.Min)
        ) {
            // Botón Restar
            IconButton(
                onClick = { onQuantityChange(item.cantidad - 1) },
                enabled = item.cantidad > 1
            ) {
                Icon(Icons.Default.Remove, contentDescription = "Restar")
            }

            // Cantidad
            Text(
                "${item.cantidad}",
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.Bold
            )

            // Botón Sumar
            IconButton(onClick = { onQuantityChange(item.cantidad + 1) }) {
                Icon(Icons.Default.Add, contentDescription = "Sumar")
            }

            // Botón Eliminar
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun CartBottomBar(subtotal: Int, total: Int, onConfirm: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Subtotal
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Subtotal:", style = MaterialTheme.typography.bodyLarge)
                Text("$${subtotal}", style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.height(4.dp))
            // Total
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total:", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold)
                Text("$${total}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Botón de Confirmar Compra
            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Proceder a Pagar", fontWeight = FontWeight.Bold)
            }
        }
    }
}
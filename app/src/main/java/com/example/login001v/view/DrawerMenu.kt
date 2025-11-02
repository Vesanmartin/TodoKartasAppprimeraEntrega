package com.example.login001v.view

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.login001v.R

// tarjeta individual con nombre+precio
@Composable
private fun ProductCardCompact(
    @DrawableRes imageRes: Int,
    nombre: String,
    precio: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            // Imagen pequeña a la izquierda
            Image(
                painter = painterResource(imageRes),
                contentDescription = nombre,
                modifier = Modifier
                    .size(72.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(12.dp))

            Column {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "$$precio",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

//modelo lista productos
private data class ProductoUi(
    @DrawableRes val imageRes: Int,
    val nombre: String,
    val precio: String
)

//menu lateral para lista productos
@Composable
fun DrawerMenu(
    username: String,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // HEADER con nombre usuario
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Categorías • Usuario: $username",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 8.dp)
            )
        }

        // LISTA DE PRODUCTOS
        LazyColumn(modifier = Modifier.weight(1f)) {

            // Productos con su imagen, nombre y precio
            val productos = listOf(
                ProductoUi(R.drawable.charmanderdestruccionabrazadora, "Charmander destrucción Abrazadora", "15990"),
                ProductoUi(R.drawable.mistypsyduck193182, "Misty Psyduck", "32990"),
                ProductoUi(R.drawable.charizardgx, "Charizard GX", "49990"),
                ProductoUi(R.drawable.magicthechainveil, "Magic The Chain Veil", "25990"),
                ProductoUi(R.drawable.photocardsfesta2023bts, "Festa 2023", "9900")
            )

            // tarjeta por producto
            items(productos.size) { index ->
                val p = productos[index]
                ProductCardCompact(
                    imageRes = p.imageRes,
                    nombre = p.nombre,
                    precio = p.precio
                ) {
                    val nombreSeguro = Uri.encode(p.nombre)
                    navController.navigate(
                        "ProductoFormScreen?nombre=$nombreSeguro&precio=${p.precio}&imgRes=${p.imageRes}"
                    )
                }
            }

            // Opción extra: escanear QR
            item {
                Spacer(Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    ListItem(
                        leadingContent = {
                            Icon(Icons.Default.QrCode, contentDescription = "Escanear QR")
                        },
                        headlineContent = { Text("Escanear código QR") },
                        modifier = Modifier.clickable { navController.navigate("qrScanner") }
                    )
                }
                Spacer(Modifier.height(8.dp))
            }
        }

        // BOTÓN CERRAR SESIÓN
        Button(
            onClick = {
                navController.navigate("login") { popUpTo(0) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
                .height(48.dp)
        ) {
            Text("Cerrar sesión")
        }

        // FOOTER inferior
        Text(
            text = "@ 2025 TodoKartasApp ☻☻☻",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            textAlign = TextAlign.Center
        )
    }
}

// vista previa
@Preview(showBackground = true)
@Composable
fun DrawerMenuPreview() {
    val navController = androidx.navigation.compose.rememberNavController()
    DrawerMenu(username = "admin", navController = navController)
}

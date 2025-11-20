package com.example.login001v.view

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing // Para animación lineal
import androidx.compose.animation.core.RepeatMode // Para repetir la animación
import androidx.compose.animation.core.animateFloat // Para animar valores float
import androidx.compose.animation.core.infiniteRepeatable // Para repetir infinitamente
import androidx.compose.animation.core.rememberInfiniteTransition // Para crear transición infinita
import androidx.compose.animation.core.tween // Para definir duración y tipo de animación
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.login001v.R

// Tarjeta de producto
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

// Modelo compartido
internal data class ProductoUi(
    @DrawableRes val imageRes: Int,
    val nombre: String,
    val precio: String
)

@Composable
fun DrawerMenu(
    username: String,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // HEADER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Usuario: $username",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 8.dp)
            )
        }

        // INICIO IMAGEN ANIMADA GIRANDO CONTINUAMENTE
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            val infiniteTransition = rememberInfiniteTransition(label = "rotationLoop")

            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "rotationAngle"
            )

            Image(
                painter = painterResource(R.drawable.logotodokartasreload),
                contentDescription = "Imagen girando",
                modifier = Modifier
                    .size(80.dp)
                    .rotate(rotation),
                contentScale = ContentScale.Fit
            )
        }
        // FIN IMAGEN ANIMADA

        // BOTÓN CATÁLOGO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { navController.navigate("catalogo/$username") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(0.9f)
            ) {
                Text("Ir al Catálogo Completo", fontWeight = FontWeight.Medium)
            }
        }

        // LISTA DE PRODUCTOS
        LazyColumn(modifier = Modifier.weight(1f)) {
            val productos = listOf(
                ProductoUi(R.drawable.charmanderdestruccionabrazadora, "Charmander destrucción Abrazadora", "15990"),
                ProductoUi(R.drawable.mistypsyduck193182, "Misty Psyduck", "32990"),
                ProductoUi(R.drawable.charizardgx, "Charizard GX", "49990"),
                ProductoUi(R.drawable.magicthechainveil, "Magic The Chain Veil", "25990"),
                ProductoUi(R.drawable.photocardsfesta2023bts, "Festa 2023", "9900")
            )

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

// === NUEVA OPCIÓN: NAVEGAR A POSTSCREEN ===
                Spacer(Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    ListItem(
                        leadingContent = {
                            // Puedes cambiar el icono por uno más adecuado para Posts/API
                            Icon(Icons.Default.QrCode, contentDescription = "Ver Posts")
                        },
                        headlineContent = { Text("Ver Listado de Posts API") },
                        modifier = Modifier.clickable {
                            // Navegación a la ruta definida en AppNav
                            navController.navigate("posts_list_route")
                        }
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

        // FOOTER
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

@Preview(showBackground = true)
@Composable
fun DrawerMenuPreview() {
    val navController = androidx.navigation.compose.rememberNavController()
    DrawerMenu(username = "admin", navController = navController)
}
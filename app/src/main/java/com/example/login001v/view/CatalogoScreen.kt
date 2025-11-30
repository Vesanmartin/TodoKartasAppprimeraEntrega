package com.example.login001v.view

import android.net.Uri // Para Uri.encode()
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.login001v.R
import com.example.login001v.viewmodel.CartViewModel

// MODELO DE DATOS
data class ProductoData(
    val imagenRes: Int,
    val nombre: String,
    val precio: String
)

// PANTALLA PRINCIPAL
@Composable
fun CatalogoScreen(
    username: String,
    navController: NavController,
    cartViewModel: CartViewModel

) {
    Column(modifier = Modifier.fillMaxSize()) {

        // INICIO HEADER
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
        // FIN HEADER

        // INICIO BOX "CATÁLOGO DE PRODUCTOS"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFFC8E6C9))
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Catálogo de Productos",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        // FIN BOX "CATÁLOGO DE PRODUCTOS"

        // INICIO CUADRÍCULA DE TARJETAS
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            val productos = listOf(
                ProductoData(R.drawable.charmanderdestruccionabrazadora, "Charmander Destrucción", "15990"),
                ProductoData(R.drawable.mistypsyduck193182, "Misty Psyduck", "32990"),
                ProductoData(R.drawable.charizardgx, "Charizard GX", "49990"),
                ProductoData(R.drawable.pikachu, "Pikachu", "7990"),
                ProductoData(R.drawable.mewymewthoex, "Mewtwo & Mew Tag", "47990"),
                ProductoData(R.drawable.lycanroc, "Lycanroc", "14490"),
                ProductoData(R.drawable.groudonex, "Groudon Ex", "51345"),
                ProductoData(R.drawable.kyogreex, "Kyogre Ex", "48889")
            )

            items(productos.size) { index ->
                val producto = productos[index]
                // Tarjeta cliqueable (igual que en DrawerMenu)
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            val nombreSeguro = Uri.encode(producto.nombre)
                            navController.navigate(
                                "ProductoFormScreen?nombre=$nombreSeguro&precio=${producto.precio}&imgRes=${producto.imagenRes}"
                            )
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // INICIO IMAGEN
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            Image(
                                painter = painterResource(producto.imagenRes),
                                contentDescription = producto.nombre,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        // FIN IMAGEN

                        Spacer(Modifier.height(8.dp))

                        // INICIO NOMBRE
                        Text(
                            text = producto.nombre,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                        // FIN NOMBRE

                        Spacer(Modifier.height(8.dp))

                        // INICIO BOTÓN (no funcional)
                        Button(
                            onClick = { /* Solo decorativo */ },
                            enabled = false,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Ver detalles")
                        }
                        // FIN BOTÓN
                    }
                }
            }
        }
        // FIN CUADRÍCULA DE TARJETAS

        // INICIO BOTÓN "VOLVER AL MENÚ"
        Button(
            onClick = {
                navController.navigate("DrawerMenu/$username") {
                    popUpTo("catalogo") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 4.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Text(
                "Volver al menú",
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        // FIN BOTÓN "VOLVER AL MENÚ"

        // INICIO BOTÓN "CERRAR SESIÓN"
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
        // FIN BOTÓN "CERRAR SESIÓN"

        // INICIO FOOTER
        Text(
            text = "@ 2025 TodoKartasApp ☻☻☻",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            textAlign = TextAlign.Center
        )
        // FIN FOOTER
    }
}





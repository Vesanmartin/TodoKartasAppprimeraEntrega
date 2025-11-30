package com.example.login001v.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.login001v.R // Asegúrate de que esta sea la ruta correcta a tu R

@Composable
fun QrResultScreen(
    qrContent: String,
    onScanAgain: () -> Unit // Función para volver a la pantalla de escaneo
) {
    val winningCode = "POKEMON_TCG_PRISMATIC_EVO_CONTEST_2025"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (qrContent == winningCode) {
            // Caso de código ganador
            Text(
                text = "¡QR de Concurso Válido!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Felicidades ahora estás participando por un Pokemon TCG Prismatic Evolutions - Accessory Pouch ESPAÑOL",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))

            // Muestra la imagen (Asegúrate de tener R.drawable.prize_pouch)
            // Si la imagen no está en R.drawable.prize_pouch, ajusta la referencia.
                Image(
                    painter = painterResource(id = R.drawable.prize_pouch),
                    contentDescription = "Premio: Pokemon TCG Prismatic Evolutions Accessory Pouch",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                )


        } else {
            // Caso de cualquier otro código
            Text(
                text = "QR Escaneado",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Text(
                    text = "Contenido: $qrContent",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Este código no corresponde al concurso. ¡Intenta con otro!",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onScanAgain,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Escanear otro código QR")
        }
    }
}
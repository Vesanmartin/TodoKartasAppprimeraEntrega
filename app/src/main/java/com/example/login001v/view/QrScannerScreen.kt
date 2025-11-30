package com.example.login001v.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.login001v.viewmodel.QrViewModel
import com.example.login001v.utils.QrScanner

@Composable
fun QrScannerScreen(
    viewModel: QrViewModel,
    hasCameraPermission: Boolean,
    onRequestPermission: () -> Unit
    // **IMPORTANTE:** Se elimina el argumento onQrScannedAndNavigate si no es necesario en el Composable
    // La navegación se gestiona en QrRoute.kt (LaunchedEffect)
) {
    val qrResult by viewModel.qrResult.observeAsState()
    val context = LocalContext.current
    // isScanning es manejado internamente por el scanner (al detectar se detiene)

    // Detener escaneo si ya hay resultado, para que no siga escaneando tras el éxito
    var isScanning by remember { mutableStateOf(true) }

    LaunchedEffect(qrResult) {
        if (qrResult != null) {
            isScanning = false // Detener el scanner visualmente
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!hasCameraPermission) {
            // Lógica de permiso de cámara (sin cambios)
            Text(
                "Permiso de cámara requerido",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "Para escanear códigos QR, necesitamos acceso a la cámara",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onRequestPermission
            ) {
                Text("Conceder permiso de cámara")
            }
        }
        // CAMBIO: Solo escaneamos si 'isScanning' es true
        else if (isScanning) {
            Text(
                "Escanea un código QR",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                QrScanner(
                    onQrCodeScanned = { qrContent ->
                        // 1. Procesar el QR detectado en el ViewModel
                        viewModel.onQrDetected(qrContent)
                        // 2. La navegación se dispara ahora en QrRoute.kt LaunchedEffect
                        Toast.makeText(context, "QR detectado!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxSize()
                )

                // Overlay para ayudar al escaneo (sin cambios)
                Surface(
                    modifier = Modifier
                        .size(250.dp)
                        .align(Alignment.Center),
                    color = Color.Transparent,
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(
                        2.dp,
                        MaterialTheme.colorScheme.primary
                    )
                ) {}
            }

            Spacer(Modifier.height(16.dp))
            Text(
                "Enfoca el código QR en el marco central",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "La cámara debería activarse automáticamente",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        // AÑADIDO: Muestra un mensaje de "Procesando" si el QR ya fue detectado pero no ha navegado
        else if (qrResult != null && !isScanning) {
            Text(
                "QR detectado. Procesando y navegando a resultados...",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}
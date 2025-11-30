package com.example.login001v.view

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.login001v.viewmodel.QrViewModel

@Composable
fun QrRoute(
    navController: NavController,
    vm: QrViewModel = viewModel(),
    // **NUEVO:** Recibe la función de navegación (que viene de AppNav.kt)
    onQrScannedAndNavigate: (qrContent: String) -> Unit
) {
    val ctx = LocalContext.current

    // Lógica de permisos de cámara (Se mantiene sin cambios)
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                ctx, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
        Toast.makeText(
            ctx,
            if (granted) "Permiso de cámara concedido" else "Se necesita permiso de cámara",
            Toast.LENGTH_SHORT
        ).show()
    }

    // Observa resultado para NAVEGAR
    val qrResult by vm.qrResult.observeAsState()

    LaunchedEffect(qrResult) {
        // CAMBIO CLAVE: En lugar de usar popBackStack(), usamos la función de navegación
        if (qrResult != null) {
            // 1. Llama a la función que navega a la pantalla de resultados
            onQrScannedAndNavigate(qrResult!!.content)

            // 2. Limpia el resultado, pero NO hace popBackStack aquí.
            // (La limpieza se hace después de navegar para evitar doble disparo)
        }
    }

    // Tu pantalla: le pasamos permiso y la NUEVA función de navegación
    QrScannerScreen(
        viewModel = vm,
        hasCameraPermission = hasCameraPermission,
        onRequestPermission = {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        // **NUEVO:** Pasamos una función vacía, la lógica de navegación está en el LaunchedEffect.
        // La QrScannerScreen ya no necesitará este argumento.
        // La modificaremos para que ya no pida esta lambda, sino que use el LaunchedEffect.
    )
}
package com.example.login001v.view

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.login001v.viewmodel.QrViewModel

@Composable
fun QrRoute(
    navController: NavController,
    vm: QrViewModel = viewModel(),          // usa hiltViewModel() si ocupas Hilt
    returnKey: String = "qr"                // clave para devolver el resultado
) {
    val ctx = LocalContext.current

    // Permiso actual
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                ctx, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Launcher para solicitar permiso
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

    // Observa resultado para devolverlo y volver
    val qrResult = vm.qrResult.observeAsState()

    LaunchedEffect(qrResult.value) {
        qrResult.value?.let { result ->
            // ▸ Devuelve el contenido a la pantalla anterior
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set(returnKey, result.content)
            // Limpia y vuelve
            vm.clearResult()
            navController.popBackStack()
        }
    }

    // Tu pantalla: le pasamos permiso y launcher
    QrScannerScreen(
        viewModel = vm,
        hasCameraPermission = hasCameraPermission,
        onRequestPermission = {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    )
}

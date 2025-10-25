package com.example.login001v

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.login001v.navigation.AppNav
import com.example.login001v.ui.theme.Login001VTheme
import android.Manifest
//import android.os.Bundle
import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.login001v.view.QrScannerScreen
import com.example.login001v.ui.viewmodel.QrViewModel
import com.example.login001v.utils.CameraPermissionHelper

class MainActivity : ComponentActivity() {

    private val qrViewModel: QrViewModel by viewModels()

    // Estado para controlar si tenemos permiso
    private var hasCameraPermission by mutableStateOf(false)

    // Registro para solicitar permisos
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
        if (isGranted) {
            Toast.makeText(this, "Permiso de cámara concedido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                this,
                "Se necesita permiso de cámara para escanear QR",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar permiso inicial
        hasCameraPermission = CameraPermissionHelper.hasCameraPermission(this)

        setContent {
            // Use MaterialTheme directly as a quick fix
            MaterialTheme {
                Surface {
                    QrScannerScreen(
                        viewModel = qrViewModel,
                        hasCameraPermission = hasCameraPermission,
                        onRequestPermission = {
                            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    )
                }
            }
        }

        // Observa los resultados del QR
        qrViewModel.qrResult.observe(this) { qrResult ->
            qrResult?.let { result ->
                Toast.makeText(
                    this,
                    "Código QR Detectado: ${result.content}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Actualizar estado del permiso cuando la app se reanuda
        hasCameraPermission = CameraPermissionHelper.hasCameraPermission(this)
    }

/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            AppNav()
        }
    }*/
}

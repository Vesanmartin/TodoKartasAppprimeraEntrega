package com.example.login001v.ui.login

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.login001v.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController
) {
    // ViewModel compartido con Login
    val context = LocalContext.current.applicationContext as Application
    val vm: LoginViewModel = viewModel(factory = LoginViewModelFactory(context))
    val state = vm.uiState

    var showPass by remember { mutableStateOf(false) }
    var showPass2 by remember { mutableStateOf(false) }

    val colorScheme = darkColorScheme(
        primary = Color(0xFF5D28B7),
        onPrimary = Color.White,
        surface = Color(0xFF121212),
        onSurface = Color.White
    )

    MaterialTheme(colorScheme = colorScheme) {

        Box(Modifier.fillMaxSize()) {

            // Fondo blur : OJO quedo como el el login transparente
            Image(
                painter = painterResource(R.drawable.fondobannermagic2),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
                    .blur(16.dp),
                contentScale = ContentScale.Crop
            )

            // Capa oscura
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.45f))
            )

            // Gradiente para profundizar
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Black.copy(alpha = 0.15f),
                            0.6f to Color.Transparent,
                            1f to Color.Black.copy(alpha = 0.20f)
                        )
                    )
            )

            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = { Text("Crear cuenta") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = Color.White
                        )
                    )
                }
            ) { innerPadding ->

                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .fillMaxHeight(0.90f),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1A0928).copy(alpha = 0.65f)
                        ),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(22.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                "Crear cuenta TodoKartas",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.height(12.dp))

                            Image(
                                painter = painterResource(R.drawable.logotodokartasreload),
                                contentDescription = "Logo App",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(160.dp),
                                contentScale = ContentScale.Fit
                            )

                            Spacer(Modifier.height(16.dp))

                            // USERNAME
                            OutlinedTextField(
                                value = state.username,
                                onValueChange = vm::onUsernameChange,
                                label = { Text("Nombre de usuario") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                colors = textFieldColors()
                            )

                            Spacer(Modifier.height(10.dp))

                            // EMAIL
                            OutlinedTextField(
                                value = state.email,
                                onValueChange = vm::onEmailChange,
                                label = { Text("Correo electrónico") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                colors = textFieldColors()
                            )

                            Spacer(Modifier.height(10.dp))

                            // CONTRASEÑA
                            OutlinedTextField(
                                value = state.password,
                                onValueChange = vm::onPasswordChange,
                                label = { Text("Contraseña") },
                                singleLine = true,
                                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(onClick = { showPass = !showPass }) {
                                        Icon(
                                            imageVector = if (showPass) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                            contentDescription = null
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = textFieldColors()
                            )

                            Spacer(Modifier.height(10.dp))

                            // REPETIR CONTRASEÑA
                            OutlinedTextField(
                                value = state.confirmPassword,
                                onValueChange = vm::onConfirmPasswordChange,
                                label = { Text("Repetir contraseña") },
                                singleLine = true,
                                visualTransformation = if (showPass2) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(onClick = { showPass2 = !showPass2 }) {
                                        Icon(
                                            imageVector = if (showPass2) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                            contentDescription = null
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = textFieldColors()
                            )

                            if (state.error != null) {
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = state.error!!,
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(Modifier.height(20.dp))

                            Button(
                                onClick = {
                                    vm.register {
                                        navController.popBackStack()
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .height(52.dp)
                            ) {
                                Text("Registrar usuario")
                            }

                            Spacer(Modifier.height(12.dp))

                            TextButton(
                                onClick = { navController.popBackStack() }
                            ) {
                                Text(
                                    "¿Ya tienes cuenta? Inicia sesión",
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
} // fin registro screen

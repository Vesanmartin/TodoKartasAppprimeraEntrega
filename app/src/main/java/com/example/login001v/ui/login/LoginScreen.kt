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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.login001v.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController
) {
    // ViewModel
    val context = LocalContext.current.applicationContext as Application
    val vm: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(context)
    )

    val state = vm.uiState
    var showPass by remember { mutableStateOf(false) }

    val colorScheme = darkColorScheme(
        primary = Color(0xFF5D28B7),
        onPrimary = Color.White,
        surface = Color(0xFF121212),
        onSurface = Color.White
    )

    MaterialTheme(colorScheme = colorScheme) {

        Box(Modifier.fillMaxSize()) {

            // Fondo desenfocado
            Image(
                painter = painterResource(R.drawable.fondobannermagic2),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
                    .blur(16.dp)
                    .background(Color.Black),
                contentScale = ContentScale.Crop
            )

            // Velo superior
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.45f))
            )

            // Gradiente
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
                        title = { Text("Proyecto semestre: TodoKartas") },
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
                            .fillMaxHeight(0.85f),
                        shape = RoundedCornerShape(20.dp),
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
                                "¡Bienvenido!",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.height(12.dp))

                            Image(
                                painter = painterResource(R.drawable.logotodokartasreload),
                                contentDescription = "Logo App",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.Fit
                            )

                            Spacer(Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "TodoKartas",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                                Text(
                                    "La mejor mano",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }

                            Spacer(Modifier.height(12.dp))

                            // Usuario
                            OutlinedTextField(
                                value = state.username,
                                onValueChange = vm::onUsernameChange,
                                label = { Text("Usuario") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                colors = textFieldColors()
                            )

                            Spacer(Modifier.height(8.dp))

                            // Contraseña
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
                                            contentDescription = "Toggle password"
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
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(Modifier.height(20.dp))

                            // Botón iniciar sesión
                            Button(
                                onClick = {
                                    vm.submit { user ->
                                        navController.navigate("DrawerMenu/$user") {
                                            popUpTo("login") { inclusive = true }
                                            launchSingleTop = true
                                        }
                                    }
                                },
                                enabled = !state.isLoading,
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .height(52.dp)
                            ) {
                                Text(if (state.isLoading) "Validando..." else "Iniciar sesión")
                            }

                            Spacer(Modifier.height(12.dp))

                            // botón de registro
                            TextButton(
                                onClick = { navController.navigate("registro") }
                            ) {
                                Text(
                                    "¿No tienes cuenta? Crear cuenta",
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }
                            // FIN Registro
                        }
                    }
                }
            }
        }
    }
}

// Helper para los colores de TextField
@Composable
fun textFieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = Color.White.copy(alpha = 0.12f),
    unfocusedContainerColor = Color.White.copy(alpha = 0.10f),
    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
    unfocusedIndicatorColor = Color.White.copy(alpha = 0.7f),
    cursorColor = MaterialTheme.colorScheme.primary,
    focusedLabelColor = Color.White,
    unfocusedLabelColor = Color.White.copy(alpha = 0.8f),

    //Colores de texto: Ojo me genero problemas porque no se veian

    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    disabledTextColor = Color.White.copy(alpha = 0.5f),
    errorTextColor = Color.Red

)

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}

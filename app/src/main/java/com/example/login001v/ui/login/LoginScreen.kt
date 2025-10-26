package com.example.login001v.ui.login


import android.R.attr.textStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.example.login001v.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.alpha
import androidx.compose.material3.TextFieldDefaults

@OptIn(ExperimentalMaterial3Api::class)
// Permite usar funciones Material 3 que son experimentales
@Composable  // Genera Interfaz Gráfica
fun LoginScreen(
    navController: NavController,
    vm: LoginViewModel = viewModel()
) {

    // Estado del ViewModel (usuario, contraseña, error, etc.)
    val state = vm.uiState
    var showPass by remember { mutableStateOf(false) }

    // Esquema de colores oscuro (Material 3)
    val ColorScheme = darkColorScheme(
        primary = Color(0xFF5D28B7),  // Rojo oscuro característico del proyecto
        onPrimary = Color.White,
        onSurface = Color(0xFF0C0C0C) // Gris oscuro para texto y bordes
    ) // fin darkColorScheme

    // Aplicar esquema de colores al tema Material
    MaterialTheme(
        colorScheme = ColorScheme
    ) { // inicio Aplicar Material

        //  Contenedor principal que permite superponer fondo e interfaz
        Box(modifier = Modifier.fillMaxSize()) {

            // Imagen de fondo general con efecto blur y transparencia
            Image(
                painter = painterResource(id = R.drawable.fondobannermagic2),
                contentDescription = null, // No es necesaria descripción
                modifier = Modifier
                    .fillMaxSize()
                    .blur(18.dp)   // Efecto de desenfoque suave
                    .alpha(0.9f),  // Transparencia del fondo
                contentScale = ContentScale.Crop // Escala para cubrir toda la pantalla
            )

            // Estructura principal de la pantalla (barra superior + contenido)
            Scaffold(
                containerColor = Color.Transparent, // Para dejar visible el fondo difuminado
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Proyecto semestre: TodoKartas",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    )
                } // fin topBar
            ) { innerPadding -> // Inicio del contenido interno (Inner Content)

                // Recuadro principal con imagen translúcida y contenido del login
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {

                    // Imagen translúcida que actúa como panel dentro del login
                    Image(
                        painter = painterResource(id = R.drawable.fondobannermagic2), // Imagen PNG o WebP con transparencia
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .height(580.dp)
                            .alpha(0.99f) // Controla la transparencia del panel
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop // Ajusta imagen al tamaño del cuadro
                    )

                    // Contenido que se muestra encima de la imagen translúcida
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(20.dp), // Margen interno del contenido
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // Texto de bienvenida
                        Text(
                            text = "Bienvenido !",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        // Logo principal de TodoKartas
                        Image(
                            painter = painterResource(id = R.drawable.logotodokartasreload),
                            contentDescription = "Logo App",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            contentScale = ContentScale.Fit // Ajusta sin recortar
                        )

                        // Espaciador entre logo y campos
                        Spacer(modifier = Modifier.height(32.dp))

                        // Fila con lema o subtítulo
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "TodoKartas",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Text(
                                "La mejor mano",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        //  Campo de texto para el usuario
                        OutlinedTextField(
                            value = state.username,
                            onValueChange = vm::onUsernameChange,
                            label = { Text("Usuario") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White.copy(alpha = 0.6f),   // Fondo blanco translúcido (activo)
                                unfocusedContainerColor = Color.White.copy(alpha = 0.5f), // Fondo blanco translúcido (inactivo)
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Borde color rojo oscuro (activo)
                                unfocusedIndicatorColor = Color.White.copy(alpha = 0.7f), // Borde claro (inactivo)
                                cursorColor = MaterialTheme.colorScheme.primary            // Color del cursor
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black) // Texto negro
                        )
                        //  Campo de texto para la contraseña
                        // 🔑 Campo de texto para la contraseña (con fondo blanco translúcido)
                        OutlinedTextField(
                            value = state.password,
                            onValueChange = vm::onPasswordChange,
                            label = { Text("Contraseña") },
                            singleLine = true,
                            visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                TextButton(onClick = { showPass = !showPass }) {
                                    Text(if (showPass) "Ocultar" else "Ver")
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White.copy(alpha = 0.6f),   // Fondo blanco translúcido (activo)
                                unfocusedContainerColor = Color.White.copy(alpha = 0.5f), // Fondo blanco translúcido (inactivo)
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Borde color rojo oscuro (activo)
                                unfocusedIndicatorColor = Color.White.copy(alpha = 0.7f), // Borde claro (inactivo)
                                cursorColor = MaterialTheme.colorScheme.primary            // Color del cursor
                            ),

                            // Color del texto y tamaño (aquí sí va el color del texto)
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                        )


                        //  Mostrar mensaje de error si existe
                        if (state.error != null) {
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = state.error ?: "",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Espacio antes del botón
                        Spacer(modifier = Modifier.height(32.dp))

                        // Botón de inicio de sesión
                        Button(
                            onClick = {
                                // Acción del botón: validar usuario y navegar
                                vm.submit { user ->
                                    navController.navigate("DrawerMenu/$user") {
                                        popUpTo("login") { inclusive = true } // Evita volver atrás al login
                                        launchSingleTop = true // No crea múltiples instancias
                                    }
                                }
                            },
                            enabled = !state.isLoading, // Desactiva el botón mientras valida
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .height(50.dp)
                        ) {
                            // Texto dinámico del botón
                            Text(if (state.isLoading) "Validando..." else "Iniciar sesión")
                        }
                    } // fin Column
                } // fin Box
            } // Fin inner (contenido interno del Scaffold)
        } // fin Box principal (fondo difuminado)
    } // fin Aplicar Material
} // Fin LoginScreen

// ---------------------------------------------------------
// Vista previa de la pantalla de Login
// ---------------------------------------------------------
@Preview(showBackground = true) // Genera la vista previa
@Composable  // Genera Interfaz Gráfica
fun LoginScreenPreview() {
    // Crear un navController ficticio para fines de la vista previa
    val navController = rememberNavController()

    // Crear un ViewModel simulado para la vista previa
    val vm = LoginViewModel() // Suponiendo que LoginViewModel está correctamente configurado

    // Llamar a la pantalla principal de Login
    LoginScreen(navController = navController, vm = vm)
} // Fin HomeScreen

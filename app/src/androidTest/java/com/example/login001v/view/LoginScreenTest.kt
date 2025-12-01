package com.example.login001v.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test
import com.example.login001v.ui.login.LoginScreen

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_MuestraComponentesPrincipales_Correctamente() {
        // ARRANGE (Preparar): Cargamos nuestro Composable `LoginScreen`.
        println("--- INICIANDO PRUEBA LoginScreenTest ---")

        composeTestRule.setContent {
            // Creamos un NavController falso porque el Composable lo requiere.
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }

        // --- ACT & ASSERT (Actuar y Afirmar) ---

        // 1. Verificamos que el título "¡Bienvenido!" se muestra.
        composeTestRule.onNodeWithText("¡Bienvenido!").assertIsDisplayed()
        println(" Éxito: Se encontró el título '¡Bienvenido!'.")

        // 2. Verificamos que el campo de texto para el usuario se muestra.
        composeTestRule.onNodeWithText("Usuario").assertIsDisplayed()
        println(" Éxito: Se encontró el campo de texto 'Usuario'.")

        // 3. Verificamos que el campo de texto para la contraseña se muestra.
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()
        println(" Éxito: Se encontró el campo de texto 'Contraseña'.")

        // 4. Verificamos que el botón principal de inicio de sesión se muestra.
        composeTestRule.onNodeWithText("Iniciar sesión").assertIsDisplayed()
        println(" Éxito: Se encontró el botón 'Iniciar sesión'.")

        // 5. Verificamos que el botón para ir al registro también es visible.
        composeTestRule.onNodeWithText("¿No tienes cuenta? Crear cuenta").assertIsDisplayed()
        println(" Éxito: Se encontró el enlace '¿No tienes cuenta? Crear cuenta'.")

        println("--- PRUEBA LoginScreenTest COMPLETADA CON ÉXITO ---")

    }
}
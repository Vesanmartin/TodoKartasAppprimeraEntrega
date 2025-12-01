package com.example.login001v.view



import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.login001v.ui.login.LoginScreen
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_MuestraComponentesPrincipales_Correctamente() {
        // ARRANGE (Preparar): Cargamos nuestro Composable `LoginScreen`.
        composeTestRule.setContent {
            // Creamos un NavController falso porque el Composable lo requiere.
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }

        // Comprobamos que los componentes existen y son visibles.

        // Verificamos que el título "¡Bienvenido!" se muestra.
        composeTestRule.onNodeWithText("¡Bienvenido!").assertIsDisplayed()

        // Verificamos que el campo de texto para el usuario se muestra.
        composeTestRule.onNodeWithText("Usuario").assertIsDisplayed()

        // Verificamos que el campo de texto para la contraseña se muestra.
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()

        // Verificamos que el botón principal de inicio de sesión se muestra.
        composeTestRule.onNodeWithText("Iniciar sesión").assertIsDisplayed()

        // Verificamos que el botón para ir al registro también es visible.
        composeTestRule.onNodeWithText("¿No tienes cuenta? Crear cuenta").assertIsDisplayed()
    }
}


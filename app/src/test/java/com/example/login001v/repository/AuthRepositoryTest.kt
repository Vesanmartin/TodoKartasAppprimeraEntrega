package com.example.login001v.repository

import com.example.login001v.data.model.Credential
import com.example.login001v.data.repository.AuthRepository
// Importamos herramientas para pruebas con corrutinas
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

// Importamos funciones de JUnit para hacer verificaciones (assert)
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

// Indicamos que vamos a usar funciones experimentales de corrutinas
@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryTest {

    // Instancia del repositorio para pruebas unitarias sin Contexto
    // Esto fuerza que 'dao' sea null, probando el "Caso 1: sin BD"
    private val repository = AuthRepository(context = null)

    // Asumimos que Credential.Admin tiene estos valores
    private val ADMIN_USER = "admin"
    private val ADMIN_PASS = "123"

    //----------------------------------------------------------------------------------------------
    // PRUEBAS DE LOGIN
    //----------------------------------------------------------------------------------------------

    @Test
    fun `login con credenciales validas de Admin - devuelve true`() = runTest {
        println("\n--- PRUEBA 1: Login con credenciales de Admin (dao nulo) ---")

        // ACT: Intenta iniciar sesión con las credenciales de administrador predefinidas
        val result = repository.login(ADMIN_USER, ADMIN_PASS)

        println("Paso 1: Intentando login con U:$ADMIN_USER, P:$ADMIN_PASS")

        // ASSERT: La operación de login debe ser exitosa
        assertTrue("El login de Admin debe devolver true en modo de prueba.", result)
        println(" Éxito: El login devolvió true, confirmando el acceso de Admin sin BD.")

        println("--- PRUEBA 1 FINALIZADA CON ÉXITO ---")
    }

    @Test
    fun `login con contrasena incorrecta - devuelve false`() = runTest {
        println("\n--- PRUEBA 2: Login con contraseña incorrecta (dao nulo) ---")

        val wrongPass = "wrongpassword"

        // ACT: Intenta iniciar sesión con contraseña incorrecta
        val result = repository.login(ADMIN_USER, wrongPass)

        println("Paso 1: Intentando login con U:$ADMIN_USER, P:$wrongPass")

        // ASSERT: La operación de login debe fallar
        assertFalse("El login debe devolver false con contraseña incorrecta.", result)
        println(" Éxito: El login devolvió false, como se espera.")

        println("--- PRUEBA 2 FINALIZADA CON ÉXITO ---")
    }

    //----------------------------------------------------------------------------------------------
    // PRUEBAS DE REGISTRO
    //----------------------------------------------------------------------------------------------

    @Test
    fun `register con dao nulo - devuelve false inmediatamente`() = runTest {
        println("\n--- PRUEBA 3: Registro con DAO nulo (modo de prueba) ---")

        // ARRANGE: Datos que no se deberían usar
        val user = "nuevoUser"
        val pass = "newpass"
        val email = "email@test.com"

        // ACT: Intenta registrar un usuario
        // AuthRepository.register debe devolver false si 'dao' es null
        val result = repository.register(user, pass, email)

        println("Paso 1: Intentando registro con DAO nulo.")

        // ASSERT: La operación de registro debe fallar porque no hay BD real
        assertFalse("El registro debe devolver false cuando 'dao' es null.", result)
        println(" Éxito: El registro devolvió false, confirmando que la operación fue bloqueada sin BD.")

        println("--- PRUEBA 3 FINALIZADA CON ÉXITO ---")
    }
}
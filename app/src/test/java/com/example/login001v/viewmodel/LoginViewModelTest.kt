// Archivo: LoginViewModelTest.kt (CORREGIDO)
import android.app.Application
import android.os.Looper // NUEVO: Importar Looper
import com.example.login001v.data.repository.AuthRepository
import com.example.login001v.ui.login.LoginViewModel
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelRegisterTest {

    @RelaxedMockK
    private lateinit var mockApplication: Application
    private lateinit var mockAuthRepository: AuthRepository
    private lateinit var viewModel: LoginViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        println("\n--- Configuración INICIO ---")
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // *** SOLUCIÓN AL ERROR DE LOOPER/ROOM ***
        mockkStatic(Looper::class)
        every { Looper.getMainLooper() } returns mockk()
        println("Paso Config: android.os.Looper mockeado para evitar fallos de Room/Android.")
        // ***************************************

        mockAuthRepository = mockk()
        println("Paso Config: Mock del AuthRepository creado.")

        mockkConstructor(AuthRepository::class)
        every { constructedWith<AuthRepository>(EqMatcher(mockApplication)) } returns mockAuthRepository
        println("Paso Config: Mock del AuthRepository interceptado con éxito.")

        viewModel = LoginViewModel(mockApplication)
        println("Paso Config: LoginViewModel instanciado.")
        println("--- Configuración FIN ---")
    }

    @After
    fun tearDown() {
        println("--- Limpieza INICIO ---")
        Dispatchers.resetMain()
        unmockkAll()
        println("Paso Limpieza: Dispatchers y mocks reseteados.")
        println("--- Limpieza FIN ---")
    }

    @Test
    fun `register con datos válidos - debe llamar al repositorio y ejecutar onSuccess`() = runTest {
        println("\n--- PRUEBA 1: Registro con datos válidos ---")

        // ARRANGE: Preparamos el estado del ViewModel y el comportamiento del mock
        viewModel.onUsernameChange("nuevoUsuario")
        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("123456")
        viewModel.onConfirmPasswordChange("123456")
        println("Paso 1: Credenciales válidas configuradas en ViewModel.")

        coEvery { mockAuthRepository.register("nuevoUsuario", "123456", "test@test.com") } returns true
        println("Paso 2: Definido comportamiento del mock: AuthRepository.register devolverá 'true'.")

        var onSuccessCalled = false

        // ACT: Ejecutamos la función a probar
        viewModel.register {
            onSuccessCalled = true
            println("Paso 3.1: Callback onSuccess EJECUTADO.")
        }
        println("Paso 3: Ejecutado viewModel.register().")

        // ASSERT: Verificamos los resultados
        println("Paso 4: Verificando estado final del ViewModel...")
        assertFalse("El estado de carga debería ser falso al final", viewModel.uiState.isLoading)
        println("✅ Aserción: Estado de carga es FALSO.")
        assertNull("No debería haber ningún error", viewModel.uiState.error)
        println("✅ Aserción: No hay mensaje de error.")
        assertTrue("El callback onSuccess debería haber sido llamado", onSuccessCalled)
        println("✅ Aserción: El callback onSuccess fue llamado.")

        // Verificamos que la función `register` del repositorio fue llamada
        println("Paso 5: Verificando que el repositorio fue llamado...")
        coVerify(exactly = 1) { mockAuthRepository.register("nuevoUsuario", "123456", "test@test.com") }
        println("✅ Verificación: AuthRepository.register fue llamado exactamente 1 vez con los datos correctos.")

        println("--- PRUEBA 1 FINALIZADA CON ÉXITO ---")
    }

    @Test
    fun `register con contraseñas que no coinciden - debe mostrar error y no llamar al repositorio`() = runTest {
        println("\n--- PRUEBA 2: Registro con contraseñas que NO coinciden ---")

        // ARRANGE: Datos con contraseñas diferentes
        viewModel.onUsernameChange("usuario")
        viewModel.onPasswordChange("123456")
        viewModel.onConfirmPasswordChange("diferente")
        println("Paso 1: Credenciales con contraseñas que NO coinciden configuradas.")

        // ACT: Ejecutamos la función
        viewModel.register {
            fail("onSuccess no debería ser llamado si las contraseñas no coinciden")
        }
        println("Paso 2: Ejecutado viewModel.register().")

        // ASSERT: Verificamos el estado de error
        println("Paso 3: Verificando estado de error en ViewModel...")
        assertEquals("Las contraseñas no coinciden", viewModel.uiState.error)
        println("✅ Aserción: El mensaje de error es 'Las contraseñas no coinciden'.")
        assertFalse("El estado de carga no debe cambiar", viewModel.uiState.isLoading)
        println("✅ Aserción: El estado de carga es FALSO.")

        // Verificamos que el repositorio NUNCA fue llamado
        println("Paso 4: Verificando que el repositorio NO fue llamado...")
        coVerify(exactly = 0) { mockAuthRepository.register(any(), any(), any()) }
        println("✅ Verificación: AuthRepository.register NO fue llamado (coVerify(exactly = 0)).")

        println("--- PRUEBA 2 FINALIZADA CON ÉXITO ---")
    }
}
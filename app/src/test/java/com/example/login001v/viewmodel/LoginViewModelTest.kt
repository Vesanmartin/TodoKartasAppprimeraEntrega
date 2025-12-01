import android.app.Application
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

    // Dependencias simuladas (Mocks)
    @RelaxedMockK
    private lateinit var mockApplication: Application

    // Esta es la versión falsa del repositorio que controlaremos
    private lateinit var mockAuthRepository: AuthRepository

    // Objeto que vamos a probar
    private lateinit var viewModel: LoginViewModel

    // Dispatcher para controlar las corrutinas en los tests
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // Preparamos el mock del repositorio
        mockAuthRepository = mockk()

        // Engañamos al ViewModel: cuando intente crear un AuthRepository,
        // le daremos nuestro mock en su lugar.
        mockkConstructor(AuthRepository::class)
        every { constructedWith<AuthRepository>(EqMatcher(mockApplication)) } returns mockAuthRepository

        // Creamos la instancia real del ViewModel, que ahora usará nuestro mock.
        viewModel = LoginViewModel(mockApplication)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `register con datos válidos - debe llamar al repositorio y ejecutar onSuccess`() = runTest {
        // ARRANGE: Preparamos el estado del ViewModel y el comportamiento del mock
        viewModel.onUsernameChange("nuevoUsuario")
        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("123456")
        viewModel.onConfirmPasswordChange("123456")

        // Le decimos al mock: "Cuando te llamen con estos datos, devuelve 'true' (éxito)"
        coEvery { mockAuthRepository.register("nuevoUsuario", "123456", "test@test.com") } returns true

        var onSuccessCalled = false // Flag para verificar si el callback se ejecuta

        // ACT: Ejecutamos la función a probar
        viewModel.register {
            onSuccessCalled = true
        }

        // ASSERT: Verificamos los resultados
        assertFalse("El estado de carga debería ser falso al final", viewModel.uiState.isLoading)
        assertNull("No debería haber ningún error", viewModel.uiState.error)
        assertTrue("El callback onSuccess debería haber sido llamado", onSuccessCalled)

        // Verificamos que la función `register` del repositorio fue llamada exactamente 1 vez con los datos correctos.
        coVerify(exactly = 1) { mockAuthRepository.register("nuevoUsuario", "123456", "test@test.com") }
    }

    @Test
    fun `register con contraseñas que no coinciden - debe mostrar error y no llamar al repositorio`() = runTest {
        // ARRANGE: Datos con contraseñas diferentes
        viewModel.onUsernameChange("usuario")
        viewModel.onPasswordChange("123456")
        viewModel.onConfirmPasswordChange("diferente") // <-- La diferencia

        // ACT: Ejecutamos la función
        viewModel.register {
            fail("onSuccess no debería ser llamado si las contraseñas no coinciden")
        }

        // ASSERT: Verificamos el estado de error
        assertEquals("Las contraseñas no coinciden", viewModel.uiState.error)
        assertFalse("El estado de carga no debe cambiar", viewModel.uiState.isLoading)

        // Verificamos que el repositorio NUNCA fue llamado, porque la validación falló antes.
        coVerify(exactly = 0) { mockAuthRepository.register(any(), any(), any()) }
    }
}

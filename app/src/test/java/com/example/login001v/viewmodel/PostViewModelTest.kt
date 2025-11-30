
//*****   Debe ser instalado en: com.example.apirest.viewmodel.PostViewModelTest 


//--------------------------------------------------------------------------------------
//Este código implementa una prueba unitaria para verificar el comportamiento del PostViewModel en la arquitectura MVVM.
// PROPÓSITO PRINCIPAL
// Validar que el ViewModel maneja correctamente el estado de los posts a través de su StateFlow, simulando el flujo de datos desde el //repositorio.
// Configuración de Corrutinas: Usa UnconfinedTestDispatcher para ejecución inmediata en tests
// ViewModel Real: Testea la instancia real de PostViewModel (no un mock)
// Manipulación Directa: Asigna datos fake directamente al StateFlow interno
// Verificaciones de Estado: Confirma que los datos se reflejan correctamente en el estado público
//--------------------------------------------------------------------------------------



// Declara el paquete donde se encuentra la clase de test
package com.example.login001v.viewmodel

// Importa el modelo de datos CardImages y Post
import com.example.login001v.data.model.CardImages
import com.example.login001v.data.model.Post


// Importa los dispatchers de corrutinas
import kotlinx.coroutines.Dispatchers

// Importa la anotación para APIs experimentales de corrutinas
import kotlinx.coroutines.ExperimentalCoroutinesApi

// Importa el dispatcher de test sin confinamiento
import kotlinx.coroutines.test.UnconfinedTestDispatcher

// Importa funciones para resetear/definir el dispatcher principal
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

// JUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

// Anotación para indicar uso de APIs experimentales de corrutinas
@OptIn(ExperimentalCoroutinesApi::class)
// Clase de tests para PostViewModel
class PostViewModelTest {

    // Crea un dispatcher de test sin confinamiento para ejecución inmediata
    private val testDispatcher = UnconfinedTestDispatcher()

    // Método que se ejecuta antes de cada test
    @Before
    fun setUp() {
        // Establece el dispatcher principal para tests
        Dispatchers.setMain(testDispatcher)
    }

    // Método que se ejecuta después de cada test
    @After
    fun tearDown() {
        // Restaura el dispatcher principal original
        Dispatchers.resetMain()
    }

    // Test que verifica que cardList contiene los datos esperados
    @Test
    fun `postList contiene datos esperados`() = runTest(testDispatcher) {
        val fakeImgs = CardImages(
            small = "test",
            large = "testimg"
        )
       // lista fake de cartas
        val fakePosts = listOf(
            // Crea el primer post de prueba con datos simulados
            Post(
                id = "1",
                name = "charizard",
                supertype = "test",
                hp = "100",
                types = listOf("Fire"),
                images = fakeImgs
            ),
            // Crea el segundo post de prueba con datos simulados
            Post(
                id = "2",
                name = "charmander",
                supertype = "test",
                hp = "100",
                types = listOf("Fire"),
                images = fakeImgs
            )
        )

        // Crea una instancia del ViewModel bajo test
        val viewModel = PostViewModel()

        // ⚠ OJO: esto solo compila si _cardList es visible desde aquí (no private)
        // Simulamos que fetchPosts() completó exitosamente
        viewModel._cardList.value = fakePosts

        // Verifica que el tamaño de la lista sea el esperado
        assertEquals(2, viewModel.cardList.value.size)
        // Verifica que el nombre del primer post sea correcto
        assertEquals("charizard", viewModel.cardList.value[0].name)
        // Verifica que el HP del segundo post sea correcto
        assertEquals("100", viewModel.cardList.value[1].hp)
    }

    // Test básico de ejemplo sin lógica compleja
    @Test
    fun `test básico de ejemplo`() = runTest(testDispatcher) {
        // Test simple que no depende del ViewModel
        assertEquals(1, 1)
    }
} // fin test

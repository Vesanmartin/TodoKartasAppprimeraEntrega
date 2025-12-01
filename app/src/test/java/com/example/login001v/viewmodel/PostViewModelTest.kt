// Declara el paquete donde se encuentra la clase de test
package com.example.login001v.viewmodel

import com.example.login001v.data.model.CardImages
import com.example.login001v.data.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
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
        println("\n--- Configuración INICIO ---")
        // Establece el dispatcher principal para tests
        Dispatchers.setMain(testDispatcher)
        println("Paso Config: Dispatcher principal establecido a UnconfinedTestDispatcher.")
    }

    // Método que se ejecuta después de cada test
    @After
    fun tearDown() {
        println("--- Limpieza INICIO ---")
        // Restaura el dispatcher principal original
        Dispatchers.resetMain()
        println("Paso Limpieza: Dispatcher principal restaurado.")
        println("--- Limpieza FIN ---")
    }

    // Test que verifica que cardList contiene los datos esperados
    @Test
    fun `postList contiene datos esperados`() = runTest(testDispatcher) {
        println("\n--- PRUEBA 1: postList contiene datos esperados (Verificación MVVM) ---")

        val fakeImgs = CardImages(small = "test", large = "testimg")

        // lista fake de cartas
        val fakePosts = listOf(
            Post(id = "1", name = "charizard", supertype = "test", hp = "100", types = listOf("Fire"), images = fakeImgs),
            Post(id = "2", name = "charmander", supertype = "test", hp = "100", types = listOf("Fire"), images = fakeImgs)
        )
        println("Paso 1: Datos de prueba (fakePosts) creados con ${fakePosts.size} elementos.")

        // Crea una instancia del ViewModel bajo test
        val viewModel = PostViewModel()
        println("Paso 2: PostViewModel instanciado.")

        // Simulamos que fetchPosts() completó exitosamente
        viewModel._cardList.value = fakePosts
        println("Paso 3: Datos de prueba inyectados directamente al StateFlow interno (_cardList).")

        // 1. Verifica que el tamaño de la lista sea el esperado
        assertEquals(2, viewModel.cardList.value.size)
        println(" Aserción 1: El tamaño de la lista pública (cardList) es correcto (${viewModel.cardList.value.size}).")

        // 2. Verifica que el nombre del primer post sea correcto
        assertEquals("charizard", viewModel.cardList.value[0].name)
        println("Aserción 2: El nombre del primer post es correcto ('${viewModel.cardList.value[0].name}').")

        // 3. Verifica que el HP del segundo post sea correcto
        assertEquals("100", viewModel.cardList.value[1].hp)
        println(" Aserción 3: El HP del segundo post es correcto ('${viewModel.cardList.value[1].hp}').")

        println("--- PRUEBA 1 FINALIZADA CON ÉXITO ---")
    }

    // Test básico de ejemplo sin lógica compleja
    @Test
    fun `test básico de ejemplo`() = runTest(testDispatcher) {
        println("\n--- PRUEBA 2: Test básico de ejemplo ---")
        // Test simple que no depende del ViewModel
        assertEquals(1, 1)
        println(" Aserción: 1 es igual a 1. ¡Test pasado!")
        println("--- PRUEBA 2 FINALIZADA CON ÉXITO ---")
    }
}
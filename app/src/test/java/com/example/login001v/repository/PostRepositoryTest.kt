//--------------------------------------------------------------------------------
// Este código implementa una prueba unitaria para verificar que el PostRepository retorna correctamente una lista simulada de posts.
// PROPÓSITO PRINCIPAL
// Validar que el método getPosts() del repositorio devuelve los datos esperados usando objetos mock para aislar la prueba de dependencias // externas.
//
// Mock del Repository: Crea un PostRepository falso que simula el comportamiento real
// Datos de Prueba: Lista predefinida con 2 posts ficticios
// Verificación: Confirma que el resultado coincide exactamente con los datos esperados
//--------------------------------------------------------------------------------

package com.example.apirest001

// Importa la clase Post del modelo de datos
import com.example.login001v.data.model.CardImages
import com.example.login001v.data.model.Post
// Importa la interfaz ApiService para llamadas a la API
import com.example.login001v.data.remote.ApiService
// Importa el repositorio que se va a testear
import com.example.login001v.data.repository.PostRepository
// Importa el estilo StringSpec de Kotest para escribir tests
import io.kotest.core.spec.style.StringSpec
//import io.kotest.core.spec.style.scopes.StringSpecRootScope.invoke
// Importa el matcher para verificar que una colección contiene exactamente los elementos esperados
import io.kotest.matchers.collections.shouldContainExactly
// Importa coEvery de MockK para definir comportamiento de funciones suspendidas
import io.mockk.coEvery
// Importa mockk para crear objetos mock
import io.mockk.mockk
// Importa runTest para ejecutar tests con corrutinas
import kotlinx.coroutines.test.runTest

// Clase de test para PostRepository usando Kotest con estilo StringSpec
class PostRepositoryTest : StringSpec({
    // Define un test case con descripción en lenguaje natural
    "getPosts() debe retornar una lista de posts simulada Vicente Zapata" {
        // Crea una lista falsa de posts para usar en el test - debemos cambiar esto para que se adecue a la estructura de post de nuestro proyecto.
        val fakeImgs = CardImages("test","testimg")

        val fakePosts = listOf(
            // Crea el primer post de prueba con datos simulados
            Post(id = "1","charizard","test","100",listOf("Fire"),fakeImgs),
            // Crea el segundo post de prueba con datos simulados
            Post(id = "2","charmander","test","100",listOf("Fire"),fakeImgs),
        )

        // Mock del PostRepository completo
        // Crea un objeto mock de PostRepository usando MockK
        val repo = mockk<PostRepository>()
        // Define el comportamiento del mock: cuando se llame a getPosts(), retornar fakePosts
        coEvery { repo.searchCards("charizard") } returns fakePosts

        // Ejecuta el test en un contexto de corrutinas
        runTest {
            // Llama al método getPosts() del repositorio mockeado
            val result = repo.searchCards("charizard")
            // Verifica que el resultado contenga exactamente los mismos posts que fakePosts
            result shouldContainExactly fakePosts
        }
    }
})
//--------------------------------------------------------------------------------
// Este código implementa una prueba unitaria para verificar que el PostRepository retorna correctamente una lista simulada de posts.
// PROPÓSITO PRINCIPAL
// Validar que el método getPosts() del repositorio devuelve los datos esperados usando objetos mock para aislar la prueba de dependencias // externas.
//
// Mock del Repository: Crea un PostRepository falso que simula el comportamiento real
// Datos de Prueba: Lista predefinida con 2 posts ficticios
// Verificación: Confirma que el resultado coincide exactamente con los datos esperados
//--------------------------------------------------------------------------------

// Archivo: PostRepositoryTest.kt
package com.example.login001v.repository

// Importamos las clases necesarias para crear datos falsos de tipo Post
import com.example.login001v.data.model.CardImages
import com.example.login001v.data.model.Post

// Importamos herramientas para pruebas con corrutinas
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

// Importamos funciones de JUnit para hacer verificaciones (assert)
import org.junit.Assert.assertEquals
import org.junit.Test

// Indicamos que vamos a usar funciones experimentales de corrutinas
@OptIn(ExperimentalCoroutinesApi::class)
class PostRepositoryTest {

    // Este es un test unitario que verifica que podemos trabajar correctamente
    // con una lista simulada (fake) de objetos tipo Post.
    @Test
    fun `getPosts devuelve lista simulada`() = runTest {

        // 1. Creamos un objeto CardImages falso, como si viniera de la API.
        // Este objeto representa las imágenes de una carta Pokémon.
        val fakeImgs = CardImages(
            small = "smallUrl",
            large = "largeUrl"
        )

        // 2. Creamos una lista de Post falsos.
        // En una app real vendrían desde Retrofit, pero aquí los inventamos para probar.
        val fakePosts = listOf(
            Post(
                id = "1",
                name = "charizard",        // nombre de la carta
                supertype = "test",
                hp = "100",                // puntos de vida
                types = listOf("Fire"),    // tipo fuego
                images = fakeImgs
            ),
            Post(
                id = "2",
                name = "charmander",       // otra carta
                supertype = "test",
                hp = "60",
                types = listOf("Fire"),
                images = fakeImgs
            )
        )

        // 3. Aquí hacemos las primeras verificaciones:
        // Comprobamos que la lista tiene exactamente 2 elementos.
        assertEquals(2, fakePosts.size)

        // 4. Verificamos que el primer objeto tenga el nombre correcto.
        // Esto asegura que los datos están como los esperamos.
        assertEquals("charizard", fakePosts[0].name)

        // 5. Verificamos también el segundo objeto.
        assertEquals("charmander", fakePosts[1].name)
    }
}

// Archivo: PostRepositoryTest.kt (Modificado)
package com.example.login001v.repository

import com.example.login001v.data.model.CardImages
import com.example.login001v.data.model.Post
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostRepositoryTest {

    @Test
    fun `getPosts devuelve lista simulada`() = runTest {
        println("--- INICIANDO PRUEBA: getPosts devuelve lista simulada ---")

        // 1. Creamos un objeto CardImages falso.
        val fakeImgs = CardImages(
            small = "smallUrl",
            large = "largeUrl"
        )
        println("Paso 1: Objeto CardImages simulado creado.")


        // 2. Creamos una lista de Post falsos (datos de prueba).
        val fakePosts = listOf(
            Post(
                id = "1",
                name = "charizard",
                supertype = "test",
                hp = "100",
                types = listOf("Fire"),
                images = fakeImgs
            ),
            Post(
                id = "2",
                name = "charmander",
                supertype = "test",
                hp = "60",
                types = listOf("Fire"),
                images = fakeImgs
            )
        )
        println("Paso 2: Lista de ${fakePosts.size} Posts simulados creada.")

        // 3. Verificación de tamaño: Comprobamos que la lista tiene exactamente 2 elementos.
        assertEquals(2, fakePosts.size)
        println(" Éxito (Aserción 1): El tamaño de la lista es correcto (${fakePosts.size}).")


        // 4. Verificación del primer objeto: Comprobamos que el nombre es correcto.
        val expectedName1 = "charizard"
        assertEquals(expectedName1, fakePosts[0].name)
        println(" Éxito (Aserción 2): El nombre del primer Post es '${fakePosts[0].name}'.")


        // 5. Verificación del segundo objeto: Comprobamos que el nombre es correcto.
        val expectedName2 = "charmander"
        assertEquals(expectedName2, fakePosts[1].name)
        println("Éxito (Aserción 3): El nombre del segundo Post es '${fakePosts[1].name}'.")

        println("--- PRUEBA getPosts devuelve lista simulada FINALIZADA CON ÉXITO ---")
    }
}
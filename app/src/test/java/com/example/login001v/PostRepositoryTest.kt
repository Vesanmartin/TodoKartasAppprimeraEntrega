package com.example.login001v
import com.example.login001v.data.model.Post
import com.example.login001v.data.remote.ApiService
import com.example.login001v.data.repository.PostRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

// Creamos una subclase de PostRepository para poder inyectar el ApiService manualmente

class TestablePostRepository(private val testApi: ApiService) : PostRepository(){

    override  suspend fun getPosts(): List<Post>{
        return testApi.getPosts()
    }
}

class PostRepositoryTest: StringSpec(body = {
    "getPosts() debe retornar una lista de posts simulada" {
        //1.- Simulamos el resultado de la API
        val fakePosts = listOf(
            Post(1,1,"titulo 1", "cuerpo 1"),
            Post(2,2, "titulo 2", "cuerpo 2")
        )
        //2.- Creamos un mock de ApiService
        val mockApi = mockk<ApiService>()
        coEvery { mockApi.getPosts() } returns fakePosts
        // 3. Usamos la clase de tets inyectando el mock
        val repo = TestablePostRepository(mockApi)
        // 4. Ejecutamos el test
        runTest {
            val result = repo.getPosts()
            result shouldContainExactly fakePosts
        }
    }
})
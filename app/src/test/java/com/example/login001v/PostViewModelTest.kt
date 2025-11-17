package com.example.login001v
import com.example.login001v.data.model.Post
import com.example.login001v.viewmodel.PostViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest : StringSpec({
    "postList debe contener los datos esperados después de fetchPosts()"{
        //Creamos una subclase falsa de postviewmodel que sobrescribe el repositorio
        val fakePosts = listOf(
            Post(1,1,"titulo 1","contenido 1"),
            Post(2,2,"titulo 2","contenido 2")
        )

        val testViewModel = object : PostViewModel(){
            override fun fetchPosts(){
                _postList.value = fakePosts
            }
        }

        runTest {
            testViewModel.fetchPosts()
            testViewModel.postList.value shouldContainExactly fakePosts
        }
    }


})
package com.example.login001v

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.login001v.data.model.Post
import com.example.login001v.view.PostScreen
import com.example.login001v.viewmodel.PostViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class PostScreenTest{
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()
    @Test
    fun el_titulo_de_post_debe_aparecer_en_pantalla(){
        //Simulamos los datos que el viewModel entregaría
        val fakePosts = listOf(
            Post(userId = 1, id = 1, title = "Titulo 1", body = "Contenido 1"),
            Post(userId = 2, id = 2 , title = "titulo 2", body = "Contenido 2")
        )
        // Subclase falsa de postviewmodel con stateflow simulado
        val fakeViewModel = object : PostViewModel(){
            override val postList = MutableStateFlow(fakePosts)
        }

        // renderizamos el postscreen con el viewmodel falso
        composeRule.setContent {
            PostScreen(viewModel = fakeViewModel)
        }
        //validamos que los titulos se muestran correctamente en la ui
        composeRule.onNodeWithText("Título: Titulo 1").assertIsDisplayed()
        composeRule.onNodeWithText("Titulo: titulo 2").assertIsDisplayed()
    }
}
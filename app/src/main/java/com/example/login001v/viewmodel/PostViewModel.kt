package com.example.login001v.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login001v.data.model.Post
import com.example.login001v.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//Viewmodel que mantiene el estado de los datos obtenidos

class PostViewModel: ViewModel(){
    private val repository = PostRepository()
    //Flujo mutable que contiene la lista de posts
    private val _postList = MutableStateFlow<List<Post>>(emptyList())

    //Flujo publico de solo lectura
    val postList: StateFlow<List<Post>> = _postList

    //Se llama automáticamente al iniciar
    init {
        fetchPosts()
    }//fin init

    //funcion que obtiene los datos en segundo plano
    private fun fetchPosts(){
        viewModelScope.launch {
            try {
                _postList.value = repository.getPosts()
            } catch (e: Exception){
                println("Error al obtener datos: ${e.localizedMessage}")
            }//fin try catch
        }//fin launch
    }//fin fetchpost

}//fin POstViewModel
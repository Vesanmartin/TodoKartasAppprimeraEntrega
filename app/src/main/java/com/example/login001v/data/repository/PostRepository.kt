package com.example.login001v.data.repository

import com.example.login001v.data.model.Post
import com.example.login001v.data.remote.RetrofitInstance

//Este repositorio se encarga de acceder a los datos usando Retrofit

class PostRepository{
    //Funcion que obtiene los posts desde la API
    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }
}
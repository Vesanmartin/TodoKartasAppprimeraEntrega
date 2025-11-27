package com.example.login001v.data.repository

import com.example.login001v.data.model.Post
import com.example.login001v.data.remote.RetrofitInstance

class PostRepository{
    // Funcion que busca las cartas desde la API
    suspend fun searchCards(query: String): List<Post> {
        // Llama a la nueva función de la API
        val response = RetrofitInstance.api.searchCards(query)
        // Retorna solo la lista de cartas dentro del objeto CardResponse
        return response.data
    }
}
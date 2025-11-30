package com.example.login001v.data.remote

import com.example.login001v.data.model.CardResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Esta interfaz define los endpoints de http
interface ApiService{
    // Define una solicitud GET al endpoint /cards con el parámetro 'q' (query)
    @GET("cards")
    suspend fun searchCards(
        @Query("q") query: String // Parámetro para la búsqueda (ej: name:charizard)
    ): Response<CardResponse> // Ahora devuelve el objeto contenedor CardResponse
}
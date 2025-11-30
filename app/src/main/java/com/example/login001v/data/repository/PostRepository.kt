package com.example.login001v.data.repository

import android.util.Log
import com.example.login001v.data.model.Post
import com.example.login001v.data.remote.RetrofitInstance
import retrofit2.HttpException
import java.io.IOException

class PostRepository {

    // Función que busca las cartas desde la API
    suspend fun searchCards(query: String): List<Post> {
        return try {
            // Se modifico por que response es Response<CardResponse> para poder ejecutar en celu
            val response = RetrofitInstance.api.searchCards(query)

            if (response.isSuccessful) {
                val body = response.body()
                val lista = body?.data ?: emptyList()
                Log.d("API_TODO", "Cartas recibidas: ${lista.size}")
                lista
            } else {
                Log.e(
                    "API_TODO",
                    "HTTP error: ${response.code()} - ${response.errorBody()?.string()}"
                )
                emptyList()
            }

        } catch (e: HttpException) {
            Log.e("API_TODO", "HttpException: ${e.code()} - ${e.message()}", e)
            emptyList()
        } catch (e: IOException) {
            Log.e("API_TODO", "IOException (problema de red / timeout)", e)
            emptyList()
        } catch (e: Exception) {
            Log.e("API_TODO", "EXCEPCIÓN desconocida en Repository", e)
            emptyList()
        }
    }
}

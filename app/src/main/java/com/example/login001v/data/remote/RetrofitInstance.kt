// Archivo: RetrofitInstance.kt (Modificado)
package com.example.login001v.data.remote

import okhttp3.Interceptor // Necesitas importar esta
import okhttp3.OkHttpClient // Necesitas importar esta
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Aquí defines tu clave API
const val POKEMON_TCG_API_KEY = "661c6d07-efdf-4e2b-8c13-673533803a3b" // <<< REEMPLAZA CON TU CLAVE REAL

object RetrofitInstance{
    val BASE_URL = "https://api.pokemontcg.io/v2/"

    // 1. Crear el Interceptor
    private val authInterceptor = Interceptor { chain ->
        // Crear la solicitud original
        val originalRequest = chain.request()

        // Construir una nueva solicitud añadiendo el encabezado 'X-Api-Key'
        val newRequest = originalRequest.newBuilder()
            .header("X-Api-Key", POKEMON_TCG_API_KEY) // Clave requerida por la API
            .build()

        // Continuar con el proceso de la solicitud
        chain.proceed(newRequest)
    }

    // 2. Crear el Cliente OkHttpClient y añadir el Interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor) // Añadir el interceptor de la clave API
        .build()

    // 3. Configurar Retrofit para usar el OkHttpClient
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Asignar el cliente con el Interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
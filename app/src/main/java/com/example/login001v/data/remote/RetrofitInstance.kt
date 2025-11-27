package com.example.login001v.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit // <<< Asegúrate de tener esta importación

const val POKEMON_TCG_API_KEY = "661c6d07-efdf-4e2b-8c13-673533803a3b"

object RetrofitInstance{
    val BASE_URL = "https://api.pokemontcg.io/v2/"

    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("X-Api-Key", POKEMON_TCG_API_KEY)
            .build()
        chain.proceed(newRequest)
    }

    // Aumentamos los tiempos límite (timeouts) aquí
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        // 30 segundos para establecer la conexión
        .connectTimeout(60, TimeUnit.SECONDS)
        // 30 segundos para esperar la respuesta completa del servidor
        .readTimeout(60, TimeUnit.SECONDS)
        // 30 segundos para enviar la solicitud (menos relevante en GET)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    // Retrofit usa este cliente con los nuevos timeouts
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
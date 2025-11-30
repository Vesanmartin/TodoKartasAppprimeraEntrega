package com.example.login001v.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val POKEMON_TCG_API_KEY = "661c6d07-efdf-4e2b-8c13-673533803a3b"

object RetrofitInstance {

    private const val BASE_URL = "https://api.pokemontcg.io/v2/"

    // Interceptor para agregar la API Key
    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newRequest = original.newBuilder()
            .addHeader("X-Api-Key", POKEMON_TCG_API_KEY)
            .build()
        chain.proceed(newRequest)
    }

    // Interceptor para ver logs en Logcat (útil)
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(logging)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

package com.example.login001v.data.remote

import com.example.login001v.data.model.Post
import retrofit2.http.GET

//Esta interfaz define los endpoints de http
interface ApiService{
    //DEfine una solicitud GET al endpoint /posts
    @GET("/posts")
    suspend fun getPosts(): List<Post>
}//fin interface
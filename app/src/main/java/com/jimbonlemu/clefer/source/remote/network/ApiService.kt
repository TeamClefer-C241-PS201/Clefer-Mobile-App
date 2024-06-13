package com.jimbonlemu.clefer.source.remote.network

import com.jimbonlemu.clefer.source.remote.response.AllArticleResponse
import com.jimbonlemu.clefer.source.remote.response.LoginRequest
import com.jimbonlemu.clefer.source.remote.response.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("articles")
    suspend fun getAllArticles(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<AllArticleResponse>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("articles/{id}")
     fun getArticleById(
        @Path("id") id: Int
    ): Call<AllArticleResponse>





}
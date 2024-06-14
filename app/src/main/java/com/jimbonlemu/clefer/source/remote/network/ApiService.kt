package com.jimbonlemu.clefer.source.remote.network

import com.jimbonlemu.clefer.source.remote.request.DiscussionRequest
import com.jimbonlemu.clefer.source.remote.request.LoginRequest
import com.jimbonlemu.clefer.source.remote.request.RegisterRequest
import com.jimbonlemu.clefer.source.remote.response.AllArticleResponse
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponse
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
import com.jimbonlemu.clefer.source.remote.response.CreateDiscussionResponse
import com.jimbonlemu.clefer.source.remote.response.LoginResponse
import com.jimbonlemu.clefer.source.remote.response.RegisterResponse
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

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @GET("articles/{id}")
     fun getArticleById(
        @Path("id") id: Int
    ): Call<AllArticleResponse>

     @GET("posts")
     suspend fun getAllDiscussion(): List<AllDiscussionResponseItem>

     @POST("posts")
     suspend fun createDiscussion(@Body discussionRequest: DiscussionRequest): CreateDiscussionResponse

     //get detail discussion id for shared preferences
     @GET("posts/{id}")
     suspend fun getDiscussionById(
        @Path("id") id: Int
    ): AllDiscussionResponseItem



}
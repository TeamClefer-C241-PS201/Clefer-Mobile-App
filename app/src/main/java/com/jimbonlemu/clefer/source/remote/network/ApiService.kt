package com.jimbonlemu.clefer.source.remote.network

import com.jimbonlemu.clefer.source.remote.response.AllArticleResponse
import com.jimbonlemu.clefer.source.remote.response.AnalyzeResultResponse
import com.jimbonlemu.clefer.source.remote.response.DataItemItem
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Call


interface ApiService {
    @GET("articles")
    suspend fun getAllArticles(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<AllArticleResponse>


    @Multipart
    @POST("predict")
    suspend fun postImageToAnalyze(
        @Part photo: MultipartBody.Part,
    ): AnalyzeResultResponse

    @GET("articles/{id}")
     fun getArticleById(
        @Path("id") id: Int
    ): Call<AllArticleResponse>





}
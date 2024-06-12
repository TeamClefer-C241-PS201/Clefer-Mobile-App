package com.jimbonlemu.clefer.source.remote.network

import com.jimbonlemu.clefer.source.remote.response.AllArticleResponse
import com.jimbonlemu.clefer.source.remote.response.AnalyzeResultResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @GET("everything")
    suspend fun getAllArticles(
        @Query("q") query: String,
        @Query("from") fromDate: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<AllArticleResponse>


    @Multipart
    @POST("predict")
    suspend fun postImageToAnalyze(
        @Part photo: MultipartBody.Part,
    ): AnalyzeResultResponse
}
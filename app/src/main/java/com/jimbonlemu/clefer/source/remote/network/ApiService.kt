package com.jimbonlemu.clefer.source.remote.network

import com.jimbonlemu.clefer.BuildConfig
import com.jimbonlemu.clefer.source.remote.response.AllArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("everything")
    suspend fun getAllArticles(
        @Query("q") query: String,
        @Query("from") fromDate: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<AllArticleResponse>
}
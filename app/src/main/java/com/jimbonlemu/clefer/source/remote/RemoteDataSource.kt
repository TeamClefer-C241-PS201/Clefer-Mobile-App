package com.jimbonlemu.clefer.source.remote

import com.jimbonlemu.clefer.source.remote.network.ApiService

class RemoteDataSource (private val api: ApiService) {
    suspend fun getAllArticles(
        query: String,
        fromDate: String,
        sortBy: String,
        apiKey: String,
        page: Int,
        size: Int
    ) = api.getAllArticles(query, fromDate, sortBy, apiKey, page, size)
}
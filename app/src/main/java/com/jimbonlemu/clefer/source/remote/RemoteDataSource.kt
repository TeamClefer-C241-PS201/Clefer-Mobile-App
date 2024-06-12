package com.jimbonlemu.clefer.source.remote

import com.jimbonlemu.clefer.source.remote.network.ApiService

class RemoteDataSource (private val api: ApiService) {
    suspend fun getAllArticles(
        page: Int,
        size: Int
    ) = api.getAllArticles(page, size)
}
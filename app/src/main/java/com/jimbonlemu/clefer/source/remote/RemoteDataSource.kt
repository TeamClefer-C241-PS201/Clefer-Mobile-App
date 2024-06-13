package com.jimbonlemu.clefer.source.remote

import com.jimbonlemu.clefer.source.remote.network.ApiService
import com.jimbonlemu.clefer.source.remote.response.LoginRequest
import com.jimbonlemu.clefer.source.remote.response.RegisterRequest

class RemoteDataSource (private val api: ApiService) {
    suspend fun getAllArticles(
        page: Int,
        size: Int
    ) = api.getAllArticles(page, size)
    fun getArticle(id: Int) = api.getArticleById(id)

    suspend fun login (
        loginRequest: LoginRequest
    ) = api.login(loginRequest)

    suspend fun register(
        registerRequest: RegisterRequest
    ) = api.register(registerRequest)
}
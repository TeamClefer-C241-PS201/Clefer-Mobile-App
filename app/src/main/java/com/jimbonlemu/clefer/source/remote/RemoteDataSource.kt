package com.jimbonlemu.clefer.source.remote

import com.jimbonlemu.clefer.source.remote.network.ApiService
import com.jimbonlemu.clefer.source.remote.request.DiscussionRequest
import com.jimbonlemu.clefer.source.remote.request.LoginRequest
import com.jimbonlemu.clefer.source.remote.request.RegisterRequest

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

    suspend fun getAllDiscussion() = api.getAllDiscussion()
    suspend fun createDiscussion(discussionRequest: DiscussionRequest) = api.createDiscussion(discussionRequest)
}
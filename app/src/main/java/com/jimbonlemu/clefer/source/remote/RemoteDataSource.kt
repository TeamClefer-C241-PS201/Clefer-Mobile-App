package com.jimbonlemu.clefer.source.remote

import com.jimbonlemu.clefer.source.remote.network.ApiService
import com.jimbonlemu.clefer.source.remote.request.CommentRequest
import com.jimbonlemu.clefer.source.remote.request.DiscussionRequest
import com.jimbonlemu.clefer.source.remote.request.LoginRequest
import com.jimbonlemu.clefer.source.remote.request.RegisterRequest
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem

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

    suspend fun getAllDiscussion(): List<AllDiscussionResponseItem> {
        return api.getAllDiscussion()
    }

    suspend fun createDiscussion(discussionRequest: DiscussionRequest) = api.createDiscussion(discussionRequest)

    suspend fun getDiscussionById(postId: Int) = api.getDiscussionById(postId)

   suspend fun getCommentDiscussionById(postId: Int) = api.getCommentsByPostId(postId)

    suspend fun createCommentDiscussionById(postId: Int, commentRequest: CommentRequest) = api.createComment(postId, commentRequest)

    suspend fun likeDiscussion(postId: Int) = api.likeDiscussion(postId)

    suspend fun likeComment(postId: Int, commentId: Int) = api.likeComment(postId ,commentId)

}
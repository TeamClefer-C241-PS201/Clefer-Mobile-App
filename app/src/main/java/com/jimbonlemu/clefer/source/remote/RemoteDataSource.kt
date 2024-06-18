package com.jimbonlemu.clefer.source.remote

import com.jimbonlemu.clefer.source.remote.network.ApiService
import com.jimbonlemu.clefer.source.remote.request.CommentRequest
import com.jimbonlemu.clefer.source.remote.request.DiscussionRequest
import com.jimbonlemu.clefer.source.remote.request.LoginRequest
import com.jimbonlemu.clefer.source.remote.request.RegisterRequest
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteDataSource(private val api: ApiService) {
    suspend fun getAllArticles(
        page: Int,
        size: Int,
    ) = api.getAllArticles(page, size)

    fun getArticle(id: Int) = api.getArticleById(id)

    suspend fun login(
        loginRequest: LoginRequest,
    ) = api.login(loginRequest)

    suspend fun register(
        registerRequest: RegisterRequest,
    ) = api.register(registerRequest)

    suspend fun predictImage(fileName: String, body: RequestBody) = api.predictImage(
        MultipartBody.Part.createFormData("image", fileName, body),
    )

    suspend fun updateUser(
        name: RequestBody,
        userPhoto: MultipartBody.Part?,
        username: RequestBody,
        email: RequestBody
    ) = api.updateUser( name, userPhoto, username, email)

    suspend fun getUserData() = api.getUserData()

    suspend fun getAllDiscussion(): List<AllDiscussionResponseItem> {
        return api.getAllDiscussion()
    }

    suspend fun createDiscussion(discussionRequest: DiscussionRequest) =
        api.createDiscussion(discussionRequest)

    suspend fun getDiscussionById(postId: Int) = api.getDiscussionById(postId)

    suspend fun getCommentDiscussionById(postId: Int) = api.getCommentsByPostId(postId)

    suspend fun createCommentDiscussionById(postId: Int, commentRequest: CommentRequest) =
        api.createComment(postId, commentRequest)

    suspend fun likeDiscussion(postId: Int) = api.likeDiscussion(postId)

    suspend fun likeComment(postId: Int, commentId: Int) = api.likeComment(postId, commentId)

}
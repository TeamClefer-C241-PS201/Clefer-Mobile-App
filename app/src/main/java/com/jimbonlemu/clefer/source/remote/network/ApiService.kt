package com.jimbonlemu.clefer.source.remote.network

import com.jimbonlemu.clefer.source.remote.request.CommentRequest
import com.jimbonlemu.clefer.source.remote.request.DiscussionRequest
import com.jimbonlemu.clefer.source.remote.request.LoginRequest
import com.jimbonlemu.clefer.source.remote.request.RegisterRequest
import com.jimbonlemu.clefer.source.remote.response.AllArticleResponse
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
import com.jimbonlemu.clefer.source.remote.response.CommentDiscussionResponseItem
import com.jimbonlemu.clefer.source.remote.response.CreateDiscussionResponse
import com.jimbonlemu.clefer.source.remote.response.LikeCommentResponse
import com.jimbonlemu.clefer.source.remote.response.LikeDiscussionResponse
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

     @GET("posts/{id}")
     suspend fun getDiscussionById(
        @Path("id") postId: Int
    ): AllDiscussionResponseItem

    @GET("posts/{id}/comments")
    suspend fun getCommentsByPostId(
        @Path("id") postId: Int
    ): List<CommentDiscussionResponseItem>

    @POST("posts/{id}/comments/create")
    suspend fun createComment(
        @Path("id") postId: Int,
        @Body commentRequest: CommentRequest
    ): CommentDiscussionResponseItem

    @POST("posts/{id}/like")
    suspend fun likeDiscussion(@Path("id") postId: Int): LikeDiscussionResponse

    @POST("posts/{Id}/{commentId}/like")
    suspend fun likeComment(
        @Path("id") postId: Int,
        @Path("commentId") commentId: Int
    ): LikeCommentResponse


}
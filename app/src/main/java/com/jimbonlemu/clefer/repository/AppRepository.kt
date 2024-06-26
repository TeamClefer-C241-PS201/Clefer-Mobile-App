package com.jimbonlemu.clefer.repository

import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.gson.Gson
import com.jimbonlemu.clefer.di.modules.KoinModules
import com.jimbonlemu.clefer.source.local.LocalDataSource
import com.jimbonlemu.clefer.source.local.entity.FavoriteArticle
import com.jimbonlemu.clefer.source.local.entity.HistoryAnalyzed
import com.jimbonlemu.clefer.source.remote.RemoteDataSource
import com.jimbonlemu.clefer.source.remote.request.CommentRequest
import com.jimbonlemu.clefer.source.remote.request.DiscussionRequest
import com.jimbonlemu.clefer.source.remote.request.LoginRequest
import com.jimbonlemu.clefer.source.remote.request.RegisterRequest
import com.jimbonlemu.clefer.source.remote.response.AllArticleResponse
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
import com.jimbonlemu.clefer.source.remote.response.CommentDiscussionResponseItem
import com.jimbonlemu.clefer.source.remote.response.CreateDiscussionResponse
import com.jimbonlemu.clefer.source.remote.response.DataItemItem
import com.jimbonlemu.clefer.source.remote.response.LikeCommentResponse
import com.jimbonlemu.clefer.source.remote.response.LikeDiscussionResponse
import com.jimbonlemu.clefer.source.remote.response.LoginResponse
import com.jimbonlemu.clefer.source.remote.response.LoginResult
import com.jimbonlemu.clefer.source.remote.response.PredictResponse
import com.jimbonlemu.clefer.source.remote.response.RegisterResponse
import com.jimbonlemu.clefer.utils.Prefs
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.utils.compressed
import com.jimbonlemu.clefer.views.article.paging.ArticlePaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File


class AppRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) {
    private val _detail = MutableLiveData<DataItemItem?>()
    val detail: LiveData<DataItemItem?> = _detail

    //REMOTE
    fun getAllArticles(): LiveData<PagingData<DataItemItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 4
            ),
            pagingSourceFactory = {
                ArticlePaging(remoteDataSource)
            }
        ).liveData
    }

    fun login(loginDto: LoginRequest): Flow<ResponseState<LoginResponse>> = flow {
        try {
            emit(ResponseState.Loading)
            val response = remoteDataSource.login(loginDto)
            if (response.error == true) {
                emit(ResponseState.Error(response.message.toString()))
            } else {
                val loginResult = response.user
                if (loginResult != null) {
                    Prefs.setLoginPrefs(loginResult)
                    KoinModules.reloadModule()
                }
                emit(ResponseState.Success(response))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = if (!errorBody.isNullOrEmpty()) {
                val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
                errorResponse.message
            } else {
                e.message()
            }
            emit(ResponseState.Error(errorMessage.toString()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseState.Error(e.message.toString()))
        }
    }


    fun register(registerDTO: RegisterRequest): Flow<ResponseState<RegisterResponse>> = flow {
        try {
            emit(ResponseState.Loading)
            val response = remoteDataSource.register(registerDTO)
            if (response.error == true) {
                emit(ResponseState.Error(response.message.toString()))
            } else {
                emit(ResponseState.Success(response))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = if (!errorBody.isNullOrEmpty()) {
                val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
                errorResponse.message
            } else {
                e.message()
            }
            emit(ResponseState.Error(errorMessage.toString()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    fun predictImage(fileName: Uri): Flow<ResponseState<PredictResponse>> = flow {
        try {
            emit(ResponseState.Loading)
            val image = fileName.toFile().compressed()
            val requestBody = image.asRequestBody("image/*".toMediaType())
            val response = remoteDataSource.predictImage(image.name, requestBody)
            if (response.error == true) {
                emit(ResponseState.Error(response.message.toString()))
            } else {
                emit(ResponseState.Success(response))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = if (!errorBody.isNullOrEmpty()) {
                val errorResponse = Gson().fromJson(errorBody, PredictResponse::class.java)
                errorResponse.message
            } else {
                e.message()
            }
            emit(ResponseState.Error(errorMessage.toString()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    fun updateUser(
        name: String,
        userPhotoUri: Uri?,
        username: String,
        email: String,
    ): Flow<ResponseState<LoginResponse>> = flow {
        try {
            emit(ResponseState.Loading)
            val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val usernameBody = username.toRequestBody("text/plain".toMediaTypeOrNull())
            val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())

            val userPhotoPart = userPhotoUri?.let { uri ->
                val file = File(uri.path ?: "")
                if (file.exists()) {
                    val userPhotoBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("userPhoto", file.name, userPhotoBody)
                } else {
                    null
                }
            }
            val response =
                remoteDataSource.updateUser(nameBody, userPhotoPart, usernameBody, emailBody)

            emit(ResponseState.Success(response))

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = if (!errorBody.isNullOrEmpty()) {
                val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
                errorResponse.message
            } else {
                e.message()
            }
            emit(ResponseState.Error(errorMessage.toString()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    fun getUserData(): Flow<ResponseState<LoginResult>> = flow {
        try {
            emit(ResponseState.Loading)
            val response = remoteDataSource.getUserData()
            Prefs.setUserPref(response)
            KoinModules.reloadModule()
            emit(ResponseState.Success(response))

        } catch (e: Exception) {
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = if (!errorBody.isNullOrEmpty()) {
                    val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
                    errorResponse.message
                } else {
                    e.message()
                }
                emit(ResponseState.Error(errorMessage.toString()))
            }
            e.printStackTrace()
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    fun getAllDiscussion(): Flow<ResponseState<List<AllDiscussionResponseItem>>> = flow {
        try {
            emit(ResponseState.Loading)
            val response = remoteDataSource.getAllDiscussion()
            emit(ResponseState.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    fun createDiscussion(discussionRequest: DiscussionRequest): Flow<ResponseState<CreateDiscussionResponse>> =
        flow {
            try {
                emit(ResponseState.Loading)
                val response = remoteDataSource.createDiscussion(discussionRequest)
                emit(ResponseState.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResponseState.Error(e.message.toString()))
            }
        }

    fun getDiscussionById(postId: Int): Flow<ResponseState<AllDiscussionResponseItem>> = flow {
        try {
            emit(ResponseState.Loading)
            val response = remoteDataSource.getDiscussionById(postId)
            emit(ResponseState.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    fun getCommentById(postId: Int): Flow<ResponseState<List<CommentDiscussionResponseItem>>> =
        flow {
            try {
                emit(ResponseState.Loading)
                val response = remoteDataSource.getCommentDiscussionById(postId)
                emit(ResponseState.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResponseState.Error(e.message.toString()))
            }
        }

    fun createCommentById(
        postId: Int,
        commentRequest: CommentRequest,
    ): Flow<ResponseState<CommentDiscussionResponseItem>> = flow {
        try {
            emit(ResponseState.Loading)
            val response = remoteDataSource.createCommentDiscussionById(postId, commentRequest)
            emit(ResponseState.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    fun logout(): Boolean {
        return try {
            Prefs.clearAuthPrefs()
            KoinModules.reloadModule()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    fun getDetailArticle(id: Int) {
        val responseDetail = remoteDataSource.getArticle(id)
        responseDetail.enqueue(object : Callback<AllArticleResponse> {
            override fun onResponse(
                call: Call<AllArticleResponse>,
                response: Response<AllArticleResponse>,
            ) {
                if (response.isSuccessful) {
                    val articleItem = response.body()?.data?.flatten()?.firstOrNull()
                    _detail.value = articleItem
                } else {
                    _detail.value = null
                }
            }

            override fun onFailure(call: Call<AllArticleResponse>, t: Throwable) {
                _detail.value = null
            }
        })
    }

    fun likeDiscussion(postId: Int): Flow<ResponseState<LikeDiscussionResponse>> = flow {
        try {
            emit(ResponseState.Loading)
            val response = remoteDataSource.likeDiscussion(postId)
            emit(ResponseState.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    fun likeComment(postId: Int, commentId: Int): Flow<ResponseState<LikeCommentResponse>> = flow {
        try {
            emit(ResponseState.Loading)
            val response = remoteDataSource.likeComment(postId, commentId)
            emit(ResponseState.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    //LOCAL

    //    ARTICLE
    suspend fun insertFavoriteArticle(favoriteArticle: FavoriteArticle) {
        localDataSource.insertFavoriteArticle(favoriteArticle)
    }

    fun getAllFavoriteArticles(ownerId: String): LiveData<List<FavoriteArticle>> {
        return localDataSource.getAllFavoriteArticles(ownerId)
    }

    suspend fun checkFavoriteById(ownerId: String, articleId: String): String {
        return localDataSource.checkFavoriteById(ownerId, articleId)
    }

    suspend fun deleteFavorite(id: Int) {
        localDataSource.deleteFavorite(id)
    }

//    HISTORY

    suspend fun insertHistoryAnalyzed(historyAnalyzed: HistoryAnalyzed) {
        localDataSource.insertHistoryAnalyzed(historyAnalyzed)
    }

    fun getAllHistoryAnalyzedByOwner(ownerId: String): LiveData<List<HistoryAnalyzed>> {
        return localDataSource.getAllHistoryAnalyzedByOwner(ownerId)
    }

    suspend fun deleteHistoryAnalyzed(id: String) {
        localDataSource.deleteHistoryAnalyzed(id)
    }

}
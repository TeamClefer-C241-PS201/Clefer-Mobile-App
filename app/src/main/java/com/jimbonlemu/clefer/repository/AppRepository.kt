package com.jimbonlemu.clefer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.gson.Gson
import com.jimbonlemu.clefer.source.local.LocalDataSource
import com.jimbonlemu.clefer.source.local.entity.FavoriteArticle
import com.jimbonlemu.clefer.source.remote.RemoteDataSource
import com.jimbonlemu.clefer.source.remote.response.AllArticleResponse
import com.jimbonlemu.clefer.source.remote.response.DataItemItem
import com.jimbonlemu.clefer.source.remote.response.LoginRequest
import com.jimbonlemu.clefer.source.remote.response.LoginResponse
import com.jimbonlemu.clefer.source.remote.response.RegisterRequest
import com.jimbonlemu.clefer.source.remote.response.RegisterResponse
import com.jimbonlemu.clefer.utils.Prefs
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.views.article.paging.ArticlePaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


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
            val loginResult = response.user
            if (loginResult != null) {
                Prefs.setLoginPrefs(loginResult)
            }
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

    fun register(registerDTO: RegisterRequest): Flow<ResponseState<RegisterResponse>> = flow {
        try {
            emit(ResponseState.Loading)
            val response = remoteDataSource.register(registerDTO)
            if (response.message.toBoolean()) {
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

    fun logout(): Boolean {
        return try {
            Prefs.clearAllPreferences()
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
                    // Flatten the nested list and get the first DataItemItem
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

    //LOCAL
    suspend fun insertFavoriteArticle(favoriteArticle: FavoriteArticle) {
        localDataSource.insertFavoriteArticle(favoriteArticle)
    }

    fun getAllFavoriteArticles(): LiveData<List<FavoriteArticle>> {
        return localDataSource.getAllFavoriteArticles()
    }

    suspend fun checkFavoriteById(id: Int): Int {
        return localDataSource.checkFavoriteById(id)
    }

    suspend fun deleteFavorite(id: Int) {
        localDataSource.deleteFavorite(id)
    }


}
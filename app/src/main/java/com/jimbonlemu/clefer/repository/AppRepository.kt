package com.jimbonlemu.clefer.repository

import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.jimbonlemu.clefer.source.local.LocalDataSource
import com.jimbonlemu.clefer.source.local.entity.FavoriteArticle
import com.jimbonlemu.clefer.source.remote.RemoteDataSource
import com.jimbonlemu.clefer.source.remote.network.ApiService
import com.jimbonlemu.clefer.source.remote.response.AllArticleResponse
import com.jimbonlemu.clefer.source.remote.response.AnalyzeResultResponse
import com.jimbonlemu.clefer.source.remote.response.DataItemItem
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.views.article.paging.ArticlePaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.HttpException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Callback
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

    fun postImageToAnalyze(
        imageUri: Uri,
        postService: ApiService,
    ): Flow<ResponseState<AnalyzeResultResponse>> = flow {
        try {
            emit(ResponseState.Loading)
            val photo = imageUri.toFile()
            val requestImageFile = photo.asRequestBody("image/*".toMediaType())

            val response = postService.postImageToAnalyze(
                MultipartBody.Part.createFormData("photo", photo.name, requestImageFile)
            )

            if (response.status.toBoolean()) {
                emit(ResponseState.Error(response.message.toString()))
            } else {
                emit(ResponseState.Success(response))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    fun getDetailArticle(id: Int) {
        val responseDetail = remoteDataSource.getArticle(id)
        responseDetail.enqueue(object : Callback<AllArticleResponse> {
            override fun onResponse(
                call: Call<AllArticleResponse>,
                response: Response<AllArticleResponse>
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
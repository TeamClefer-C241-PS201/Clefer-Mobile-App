package com.jimbonlemu.clefer.repository

import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.jimbonlemu.clefer.source.local.LocalDataSource
import com.jimbonlemu.clefer.source.remote.RemoteDataSource
import com.jimbonlemu.clefer.source.remote.network.ApiService
import com.jimbonlemu.clefer.source.remote.response.AnalyzeResultResponse
import com.jimbonlemu.clefer.source.remote.response.DataItemItem
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.views.article.paging.ArticlePaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class AppRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) {
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

}
package com.jimbonlemu.clefer.repository

import com.jimbonlemu.clefer.views.article.paging.ArticlePaging
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.jimbonlemu.clefer.source.local.LocalDataSource
import com.jimbonlemu.clefer.source.remote.RemoteDataSource
import com.jimbonlemu.clefer.source.remote.response.ArticlesItem


class AppRepository (private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource) {
    fun getAllArticles(): LiveData<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                ArticlePaging(remoteDataSource)
            }
        ).liveData
    }

}
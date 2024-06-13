package com.jimbonlemu.clefer.views.article.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jimbonlemu.clefer.repository.AppRepository
import com.jimbonlemu.clefer.source.remote.response.DataItemItem


class ArticleViewModel(private val repository: AppRepository) : ViewModel() {
    val detail = repository.detail

    val getAllArticles: LiveData<PagingData<DataItemItem>> = repository.getAllArticles().cachedIn(viewModelScope)
fun getDetail(id: Int) = repository.getDetailArticle(id)

}

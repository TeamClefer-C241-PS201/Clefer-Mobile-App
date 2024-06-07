package com.jimbonlemu.clefer.views.article.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jimbonlemu.clefer.repository.AppRepository
import com.jimbonlemu.clefer.source.remote.response.ArticlesItem

class ArticleViewModel(repository: AppRepository): ViewModel() {
    val getAllArticles : LiveData<PagingData<ArticlesItem>> = repository.getAllArticles().cachedIn(viewModelScope)
}
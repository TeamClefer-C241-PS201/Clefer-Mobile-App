package com.jimbonlemu.clefer.views.article.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jimbonlemu.clefer.repository.AppRepository
import com.jimbonlemu.clefer.source.local.entity.FavoriteArticle
import com.jimbonlemu.clefer.source.remote.response.DataItemItem
import kotlinx.coroutines.launch

class ArticleViewModel(private val repository: AppRepository) : ViewModel() {
    val detail = repository.detail

    val getAllArticles: LiveData<PagingData<DataItemItem>> =
        repository.getAllArticles().cachedIn(viewModelScope)

    fun getDetail(id: Int) = repository.getDetailArticle(id)

    fun insertFavoriteArticle(favoriteArticle: FavoriteArticle) {
        viewModelScope.launch {
            repository.insertFavoriteArticle(favoriteArticle)
        }
    }

    suspend fun checkFavoriteById(ownerId : String, articleId:String): String {
        return repository.checkFavoriteById(ownerId, articleId)
    }

    fun deleteFavorite(id: Int) {
        viewModelScope.launch {
            repository.deleteFavorite(id)
        }
    }

    fun getAllFavoriteArticles(ownerId: String): LiveData<List<FavoriteArticle>> {
        return repository.getAllFavoriteArticles(ownerId)
    }

}

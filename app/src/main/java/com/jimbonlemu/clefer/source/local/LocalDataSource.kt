package com.jimbonlemu.clefer.source.local

import com.jimbonlemu.clefer.source.local.entity.FavoriteArticle

class LocalDataSource(private val db: AppDatabase) {
    suspend fun insertFavoriteArticle(favoriteArticle: FavoriteArticle) {
        db.daoFavorite().insertFavoriteArticle(favoriteArticle)
    }
    fun getAllFavoriteArticles() = db.daoFavorite().getAllFavoriteArticles()

    suspend fun checkFavoriteById(id: Int) = db.daoFavorite().checkFavoriteById(id)

    suspend fun deleteFavorite(id: Int) = db.daoFavorite().deleteFavorite(id)


}
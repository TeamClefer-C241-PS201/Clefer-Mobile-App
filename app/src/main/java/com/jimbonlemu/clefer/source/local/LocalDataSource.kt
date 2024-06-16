package com.jimbonlemu.clefer.source.local

import com.jimbonlemu.clefer.source.local.entity.FavoriteArticle
import com.jimbonlemu.clefer.source.local.entity.HistoryAnalyzed

class LocalDataSource(private val db: AppDatabase) {

    //    Favorite
    suspend fun insertFavoriteArticle(favoriteArticle: FavoriteArticle) {
        db.daoFavorite().insertFavoriteArticle(favoriteArticle)
    }

    fun getAllFavoriteArticles() = db.daoFavorite().getAllFavoriteArticles()

    suspend fun checkFavoriteById(id: Int) = db.daoFavorite().checkFavoriteById(id)

    suspend fun deleteFavorite(id: Int) = db.daoFavorite().deleteFavorite(id)


    //    History
    suspend fun insertHistoryAnalyzed(historyAnalyzed: HistoryAnalyzed) {
        db.daoHistoryAnalyzed().insertHistoryAnalyzed(historyAnalyzed)
    }

    fun getAllHistoryAnalyzedByOwner(ownerId: String) =
        db.daoHistoryAnalyzed().getAllHistoryAnalyzedByOwner(ownerId)

    suspend fun deleteHistoryAnalyzed(id: String) = db.daoHistoryAnalyzed().deleteHistoryAnalyzed(id)


}
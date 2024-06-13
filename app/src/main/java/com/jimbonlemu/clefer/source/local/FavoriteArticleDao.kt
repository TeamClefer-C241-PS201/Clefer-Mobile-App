package com.jimbonlemu.clefer.source.local

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query

interface FavoriteArticleDao {
    @Insert
    suspend fun insertFavoriteArticle(favoriteArticle: FavoriteArticle)

    @Query("SELECT * FROM favorite_article")
    fun getAllFavoriteArticles(): LiveData<List<FavoriteArticle>>

    @Query("SELECT count(*) from favorite_article WHERE favorite_article.id = :id")
    suspend fun checkFavoriteByUsername(id : Int) : Int

    @Query("DELETE FROM favorite_article WHERE favorite_article.id = :id")
    suspend fun deleteFavorite(id: Int) : Int

}
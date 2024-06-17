package com.jimbonlemu.clefer.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jimbonlemu.clefer.source.local.entity.FavoriteArticle

@Dao
interface FavoriteArticleDao {
    @Insert
    suspend fun insertFavoriteArticle(favoriteArticle: FavoriteArticle)

    @Query("SELECT * FROM favorite_article WHERE favorite_article.ownerId = :ownerId")
    fun getAllFavoriteArticles(ownerId: String): LiveData<List<FavoriteArticle>>

    @Query("SELECT count(*) from favorite_article WHERE favorite_article.ownerId = :ownerId")
    suspend fun checkFavoriteById(ownerId : String) : String

    @Query("DELETE FROM favorite_article WHERE favorite_article.id = :id")
    suspend fun deleteFavorite(id: Int) : Int

}
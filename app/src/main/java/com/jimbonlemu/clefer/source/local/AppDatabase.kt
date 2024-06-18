package com.jimbonlemu.clefer.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jimbonlemu.clefer.source.local.dao.FavoriteArticleDao
import com.jimbonlemu.clefer.source.local.dao.HistoryAnalyzedDao
import com.jimbonlemu.clefer.source.local.entity.FavoriteArticle
import com.jimbonlemu.clefer.source.local.entity.HistoryAnalyzed
import com.jimbonlemu.clefer.utils.Constant.DB_VERSION

@Database(
    entities = [
        FavoriteArticle::class, HistoryAnalyzed::class
    ], version = DB_VERSION, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoFavorite(): FavoriteArticleDao
    abstract fun daoHistoryAnalyzed(): HistoryAnalyzedDao
}
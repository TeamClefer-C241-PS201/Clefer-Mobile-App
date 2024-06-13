package com.jimbonlemu.clefer.source.local

import android.content.Context
import android.provider.SyncStateContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jimbonlemu.clefer.source.local.dao.FavoriteArticleDao
import com.jimbonlemu.clefer.source.local.entity.FavoriteArticle
import com.jimbonlemu.clefer.utils.Constant



@Database(
    entities = [
        FavoriteArticle::class,
    ], version = Constant.DB_VERSION, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun daoFavorite(): FavoriteArticleDao
}
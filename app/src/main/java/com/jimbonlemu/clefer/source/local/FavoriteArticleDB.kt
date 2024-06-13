package com.jimbonlemu.clefer.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FavoriteArticle::class], version = 1)
abstract class FavoriteArticleDB : RoomDatabase() {
    companion object {
        var instance: FavoriteArticleDB? = null

        fun getDatabase(context: Context): FavoriteArticleDB {
            return instance?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteArticleDB::class.java,
                    "favorite_article_db"
                ).build()
                instance
            }
        }
    }
}
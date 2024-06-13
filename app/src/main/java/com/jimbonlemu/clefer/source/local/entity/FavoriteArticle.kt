package com.jimbonlemu.clefer.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "favorite_article")
class FavoriteArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val articleTitle: String,
    val articleDesc: String,
    val articleImg: String
)

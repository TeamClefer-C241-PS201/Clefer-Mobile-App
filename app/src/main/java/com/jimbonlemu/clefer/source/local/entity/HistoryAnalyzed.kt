package com.jimbonlemu.clefer.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_analyzed")
data class HistoryAnalyzed(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "owner_id")
    val ownerId: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "disease_result")
    val diseaseResult: String,

    @ColumnInfo(name = "disease_desc")
    val diseaseDesc: String,

    @ColumnInfo(name = "disease_suggestion")
    val diseaseSuggestion: String,

    @ColumnInfo(name = "date")
    val date: String,

    )
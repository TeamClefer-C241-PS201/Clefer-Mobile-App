package com.jimbonlemu.clefer.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jimbonlemu.clefer.source.local.entity.HistoryAnalyzed

@Dao
interface HistoryAnalyzedDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistoryAnalyzed(historyAnalyzed: HistoryAnalyzed)

    @Query("SELECT * FROM history_analyzed WHERE history_analyzed.owner_id = :ownerId")
    fun getAllHistoryAnalyzedByOwner(ownerId: String): LiveData<List<HistoryAnalyzed>>

    @Query("DELETE FROM history_analyzed WHERE history_analyzed.id = :id")
    suspend fun deleteHistoryAnalyzed(id: String) : Int

}
package com.jimbonlemu.clefer.views.dashboard.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimbonlemu.clefer.repository.AppRepository
import com.jimbonlemu.clefer.source.local.entity.HistoryAnalyzed
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: AppRepository) : ViewModel() {
    fun insertHistoryAnalyzed(historyAnalyzed: HistoryAnalyzed) {
        viewModelScope.launch {
            repository.insertHistoryAnalyzed(historyAnalyzed)
        }
    }

    fun getAllHistoryByOwner(ownerId: String) {
        repository.getAllHistoryAnalyzedByOwner(ownerId)
    }

    fun deleteHistoryAnalyzed(historyId: String) {
        viewModelScope.launch {
            repository.deleteHistoryAnalyzed(historyId)
        }
    }
}
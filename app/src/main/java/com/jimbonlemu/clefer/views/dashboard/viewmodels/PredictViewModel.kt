package com.jimbonlemu.clefer.views.dashboard.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimbonlemu.clefer.repository.AppRepository
import com.jimbonlemu.clefer.source.remote.response.PredictResponse
import com.jimbonlemu.clefer.utils.ResponseState
import kotlinx.coroutines.launch

class PredictViewModel(private val repository: AppRepository) : ViewModel() {

    private val _predictState = MutableLiveData<ResponseState<PredictResponse>>()
    val predictState: LiveData<ResponseState<PredictResponse>> get() = _predictState

    fun predictImage(uri: Uri) {
        viewModelScope.launch {
            _predictState.value = ResponseState.Loading
            repository.predictImage(uri).collect { state ->
                _predictState.postValue(state)
            }
        }
    }
}
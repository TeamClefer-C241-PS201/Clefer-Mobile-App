package com.jimbonlemu.clefer.views.profile.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimbonlemu.clefer.repository.AppRepository
import com.jimbonlemu.clefer.source.remote.response.LoginResponse
import com.jimbonlemu.clefer.source.remote.response.LoginResult
import com.jimbonlemu.clefer.utils.ResponseState
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: AppRepository) : ViewModel() {

    private val _updateUserState = MutableLiveData<ResponseState<LoginResponse>>()
    val updateUserState: LiveData<ResponseState<LoginResponse>> = _updateUserState

    private val _getUserState = MutableLiveData<ResponseState<LoginResult>>()
    val getUserState: LiveData<ResponseState<LoginResult>> = _getUserState

    fun updateUser(
        name: String,
        userPhotoUri: Uri?,
        username: String,
        email: String,
    ) {
        viewModelScope.launch {
            repository.updateUser(name, userPhotoUri, username, email).collect {
                _updateUserState.value = it
            }
        }
    }

    fun getUserData(){
        viewModelScope.launch {
            repository.getUserData().collect(){
                _getUserState.value = it
            }
        }
    }
}


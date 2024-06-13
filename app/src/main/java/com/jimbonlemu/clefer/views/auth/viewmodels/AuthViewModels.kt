package com.jimbonlemu.clefer.views.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimbonlemu.clefer.repository.AppRepository
import com.jimbonlemu.clefer.source.remote.response.LoginRequest
import com.jimbonlemu.clefer.source.remote.response.LoginResponse
import com.jimbonlemu.clefer.utils.ResponseState
import kotlinx.coroutines.launch

class AuthViewModels (private val authRepository: AppRepository) : ViewModel(){
    private val _loginResult = MutableLiveData<ResponseState<LoginResponse>>()
    val loginResult: LiveData<ResponseState<LoginResponse>> by lazy { _loginResult }


    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(LoginRequest(email, password)).collect {
                _loginResult.value = it
            }
        }
    }
}
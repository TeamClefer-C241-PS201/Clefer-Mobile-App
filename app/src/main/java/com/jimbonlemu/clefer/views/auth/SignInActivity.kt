package com.jimbonlemu.clefer.views.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isGone
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivitySignInBinding
import com.jimbonlemu.clefer.utils.CleferToast
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.views.BottomNavigationBarActivity
import com.jimbonlemu.clefer.views.auth.viewmodels.AuthViewModels
import org.koin.android.ext.android.inject

class SignInActivity : CoreActivity<ActivitySignInBinding>() {
    private val authViewModel: AuthViewModels by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setupButtonAction()
            initObservers()
            edtEmail.inputText = "tianmetalis@jgrp.org"
            edtPassword.inputText = "tianmetalis"
        }
    }

    private fun ActivitySignInBinding.setupButtonAction() {
        tvBtnRegister.setOnClickListener {
            startActivity(Intent(this@SignInActivity, RegisterActivity::class.java))
        }
        btnSignIn.setOnClickListener {
            val emailField = edtEmail.inputText.trim()
            val passField = edtPassword.inputText.trim()

            if (emailField.isEmpty()) {
                getInformToast("Don't left email empty")
            }
            if (passField.isEmpty()) {
                getInformToast("Don't left password empty")
            }
            if (emailField.isNotEmpty() && passField.isNotEmpty()) {
                authViewModel.login(emailField, passField)
            }
        }
    }

    private fun ActivitySignInBinding.initObservers() {
        authViewModel.loginResult.observe(this@SignInActivity) { response ->
            when (response) {
                is ResponseState.Loading -> {
                    enabledComponent(false)
                    getInformToast("Memproses dan memeriksa akun...")
                }

                is ResponseState.Success -> {
                    enabledComponent(true)
                    CleferToast.successToast(
                        "Berhasil Masuk!",
                        this@SignInActivity
                    )
                    startActivity(Intent(this@SignInActivity, BottomNavigationBarActivity::class.java))
                    finish()
                }

                is ResponseState.Error -> {
                    enabledComponent(true)
                    CleferToast.errorToast( response.errorMessage, this@SignInActivity)
                }
            }
        }
    }

    private fun ActivitySignInBinding.enabledComponent(isComponentEnabled: Boolean) {
        if (isComponentEnabled) {
            edtEmail.isEnabled = true
            edtPassword.isEnabled = true
            btnSignIn.isEnabled = true
        } else {
            edtEmail.isEnabled = false
            edtPassword.isEnabled = false
            btnSignIn.isEnabled = false
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivitySignInBinding =
        ActivitySignInBinding.inflate(layoutInflater)

    private fun getInformToast(msg: String) {
        CleferToast.informToast( msg, this)
    }

}
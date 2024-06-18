package com.jimbonlemu.clefer.views.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityRegisterBinding
import com.jimbonlemu.clefer.utils.CleferToast
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.views.auth.viewmodels.AuthViewModels
import org.koin.android.ext.android.inject

class RegisterActivity : CoreActivity<ActivityRegisterBinding>() {

    private val authViewModel: AuthViewModels by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            tvBtnSignIn.setOnClickListener {
                intentSignIn()
            }
            formValidation()
            setupObserver()
        }
    }

    private fun ActivityRegisterBinding.formValidation() {
        btnRegister.setOnClickListener {
            val nameField = edtUserName.inputText
            val usernameField = edtUsername.inputText.trim()
            val emailField = edtEmail.inputText.trim()
            val passwordField = edtPassword.inputText.trim()

            if (nameField.isEmpty()) {
                getInformToast("Jangan biarkan kolom Nama kosong")
            }
            if (usernameField.isEmpty()) {
                getInformToast("Jangan biarkan kolom Nama Pengguna / username kosong")
            }
            if (emailField.isEmpty()) {
                getInformToast("Jangan biarkan kolom Email kosong")
            }
            if (passwordField.isEmpty()) {
                getInformToast("Jangan biarkan kolom Kata Sandi kosong")
            }
            if (nameField.isNotEmpty() && usernameField.isNotEmpty() && emailField.isNotEmpty() && passwordField.isNotEmpty()) {
                authViewModel.register(nameField, emailField, usernameField, passwordField)
            }
        }
    }

    private fun ActivityRegisterBinding.setupObserver() {
        authViewModel.registerResult.observe(this@RegisterActivity) { response ->
            when (response) {
                is ResponseState.Loading -> {
                    isComponentEnabled(false)
                }

                is ResponseState.Success -> {
                    edtUserName.inputText = ""
                    edtUsername.inputText = ""
                    edtPassword.inputText = ""
                    edtEmail.inputText = ""
                    isComponentEnabled(true)
                    CleferToast.successToast(
                        "Suskses melakukan registrasi pengguna lakukan Login!",
                        this@RegisterActivity
                    )
                    startActivity(Intent(this@RegisterActivity, SignInActivity::class.java))
                    finish()
                }

                is ResponseState.Error -> {
                    isComponentEnabled(true)
                    CleferToast.errorToast(response.errorMessage, this@RegisterActivity)
                }
            }
        }
    }

    private fun ActivityRegisterBinding.isComponentEnabled(isEnable: Boolean) {
        btnRegister.isEnabled = isEnable
        edtUserName.isEnabled = isEnable
        edtUsername.isEnabled = isEnable
        edtEmail.isEnabled = isEnable
        edtPassword.isEnabled = isEnable
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityRegisterBinding =
        ActivityRegisterBinding.inflate(layoutInflater)

    private fun intentSignIn() {
        startActivity(Intent(this@RegisterActivity, SignInActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
        finish()
    }

    private fun getInformToast(msg: String) {
        CleferToast.informToast(msg, this@RegisterActivity)
    }


}
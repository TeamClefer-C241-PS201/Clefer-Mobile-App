package com.jimbonlemu.clefer.views.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isGone
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivitySignInBinding
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
            edtEmail.inputText = "tianmetal@gmail.com"
            edtPassword.inputText = "tianbesi"
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
                getToast("Don't left email empty")
            }
            if (passField.isEmpty()) {
                getToast("Don't left password empty")
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
                }

                is ResponseState.Success -> {
                    enabledComponent(true)
                    Toast.makeText(
                        this@SignInActivity,
                        "Sukses Login", Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@SignInActivity, BottomNavigationBarActivity::class.java))
                    finish()
                }

                is ResponseState.Error -> {
                    enabledComponent(true)
                    Toast.makeText(this@SignInActivity, response.errorMessage, Toast.LENGTH_SHORT).show()
                }

                else -> root.isGone = true
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

    private fun getToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}
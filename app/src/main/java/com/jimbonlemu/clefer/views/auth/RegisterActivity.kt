package com.jimbonlemu.clefer.views.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityRegisterBinding

class RegisterActivity : CoreActivity<ActivityRegisterBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            tvBtnSignIn.setOnClickListener {
                intentSignIn()
            }
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityRegisterBinding =
        ActivityRegisterBinding.inflate(layoutInflater)

    private fun intentSignIn() {
        startActivity(Intent(this@RegisterActivity, SignInActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
        finish()
    }

}
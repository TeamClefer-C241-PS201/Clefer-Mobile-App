package com.jimbonlemu.clefer.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivitySignInBinding

class SignInActivity : CoreActivity<ActivitySignInBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvBtnRegister.setOnClickListener {
            startActivity(Intent(this@SignInActivity, RegisterActivity::class.java))
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivitySignInBinding =
        ActivitySignInBinding.inflate(layoutInflater)

}
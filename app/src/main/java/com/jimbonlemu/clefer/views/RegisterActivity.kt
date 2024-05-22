package com.jimbonlemu.clefer.views

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityRegisterBinding
import com.jimbonlemu.clefer.databinding.ActivityWelcomeBinding

class RegisterActivity : CoreActivity<ActivityRegisterBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityRegisterBinding =
        ActivityRegisterBinding.inflate(layoutInflater)

    override fun initIntent() {
        TODO("Not yet implemented")
    }

    override fun initUI() {
        TODO("Not yet implemented")
    }

    override fun initAction() {
        TODO("Not yet implemented")
    }

    override fun initProcess() {
        TODO("Not yet implemented")
    }

    override fun initObservers() {
        TODO("Not yet implemented")
    }
}
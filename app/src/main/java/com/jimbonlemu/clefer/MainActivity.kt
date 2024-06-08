package com.jimbonlemu.clefer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityMainBinding
import com.jimbonlemu.clefer.views.auth.SplashActivity

class MainActivity : CoreActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@MainActivity, SplashActivity::class.java))
        finish()
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)
}
package com.jimbonlemu.clefer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jimbonlemu.clefer.databinding.ActivityMainBinding
import com.jimbonlemu.clefer.views.SplashActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            startActivity(Intent(this@MainActivity, SplashActivity::class.java))
            finish()
        }
    }
}
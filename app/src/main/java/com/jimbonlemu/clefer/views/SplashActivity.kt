package com.jimbonlemu.clefer.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivitySplashBinding
import com.jimbonlemu.clefer.utils.Constant
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@SuppressLint("CustomSplashScreen")
class SplashActivity : CoreActivity<ActivitySplashBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initIntent() {
        // Implement initIntent logic here or leave it empty if not used
    }

    override fun initUI() {
        playAnimation()
    }

    override fun initAction() {
        // Implement initAction logic here if needed
    }

    override fun initProcess() {
        // Implement initAction logic here if needed
        lifecycleScope.launch {
            delay(Constant.SPLASH_SCREEN_DURATION.seconds)
            startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
            finish()
        }
    }

    override fun initObservers() {
        // Implement initObservers logic here if needed
    }

    private fun playAnimation() {
        binding.apply {
            AnimatorSet().apply {
                playSequentially(
                    ObjectAnimator.ofFloat(ivSplashScreen, View.ALPHA, 1f).setDuration(700),
                    ObjectAnimator.ofFloat(tvAppName, View.ALPHA, 1f).setDuration(500),
                    ObjectAnimator.ofFloat(tvAppFullName, View.ALPHA, 1f).setDuration(500),
                )
                start()
            }
        }
    }
}
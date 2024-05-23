package com.jimbonlemu.clefer.views

import android.os.Bundle
import android.view.LayoutInflater
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityWelcomeBinding

class WelcomeActivity : CoreActivity<ActivityWelcomeBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityWelcomeBinding =
        ActivityWelcomeBinding.inflate(layoutInflater)



}
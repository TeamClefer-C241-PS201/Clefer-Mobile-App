package com.jimbonlemu.clefer.views.profile

import android.os.Bundle
import android.view.LayoutInflater
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityAboutBinding

class AboutActivity : CoreActivity<ActivityAboutBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar(binding.mToolbar)
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityAboutBinding =
        ActivityAboutBinding.inflate(layoutInflater)

}

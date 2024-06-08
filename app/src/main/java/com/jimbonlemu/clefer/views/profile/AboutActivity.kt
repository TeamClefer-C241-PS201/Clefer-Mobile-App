package com.jimbonlemu.clefer.views.profile

import android.os.Bundle
import android.view.LayoutInflater
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityAboutBinding

class AboutActivity : CoreActivity<ActivityAboutBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //add toolbar
        binding.toolbar.setupToolbar(
            title = getString(R.string.about_app),
            showBackButton = true,
            backAction = { onBackPressedDispatcher.onBackPressed() }
        )
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityAboutBinding = ActivityAboutBinding.inflate(layoutInflater)

}

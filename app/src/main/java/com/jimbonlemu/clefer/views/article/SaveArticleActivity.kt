package com.jimbonlemu.clefer.views.article

import android.os.Bundle
import android.view.LayoutInflater
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivitySaveArticleBinding

class SaveArticleActivity : CoreActivity<ActivitySaveArticleBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //add toolbar
        binding.toolbar.setupToolbar(
            title = getString(R.string.save_article),
            showBackButton = true,
            backAction = { onBackPressedDispatcher.onBackPressed() }
        )
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivitySaveArticleBinding = ActivitySaveArticleBinding.inflate(layoutInflater)


}
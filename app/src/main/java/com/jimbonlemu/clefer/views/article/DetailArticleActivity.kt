package com.jimbonlemu.clefer.views.article

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityDetailArticleBinding


class DetailArticleActivity : CoreActivity<ActivityDetailArticleBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set up Toolbar
        binding.toolbar.setupToolbar(
            title = getString(R.string.detail_artikel_title),
            showBackButton = true,
            backAction = { onBackPressedDispatcher.onBackPressed() }
        )
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityDetailArticleBinding =
        ActivityDetailArticleBinding.inflate(layoutInflater)

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }
}
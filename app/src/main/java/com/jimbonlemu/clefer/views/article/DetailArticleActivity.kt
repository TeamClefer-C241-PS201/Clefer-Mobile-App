package com.jimbonlemu.clefer.views.article

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set up Toolbar
        binding.toolbar.setupToolbar(
            title = getString(R.string.detail_artikel_title),
            showBackButton = true,
            backAction = { onBackPressed() }
        )
    }

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }
}
package com.jimbonlemu.clefer.views.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.databinding.ActivitySaveArticleBinding
import com.jimbonlemu.clefer.views.article.adapter.ArticleAdapter
import com.jimbonlemu.clefer.views.article.viewmodels.ArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SaveArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySaveArticleBinding
    private val saveArticleViewModel: ArticleViewModel by viewModel()
    private val articleAdapter = ArticleAdapter(ArticleAdapter.AdapterType.FAVORITE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        binding.toolbar.setupToolbar(
            title = getString(R.string.save_article),
            showBackButton = true,
            backAction = { onBackPressed() }
        )

        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(this@SaveArticleActivity)
            adapter = articleAdapter
        }

        saveArticleViewModel.getAllFavoriteArticles().observe(this, Observer { articles ->
            articles?.let {
                articleAdapter.setFavoriteArticles(it)
            }
        })
    }
}

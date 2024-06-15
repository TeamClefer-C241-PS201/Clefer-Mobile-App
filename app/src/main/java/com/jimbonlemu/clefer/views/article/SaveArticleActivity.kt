package com.jimbonlemu.clefer.views.article

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivitySaveArticleBinding
import com.jimbonlemu.clefer.views.article.adapter.ArticleAdapter
import com.jimbonlemu.clefer.views.article.viewmodels.ArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SaveArticleActivity : CoreActivity<ActivitySaveArticleBinding>() {
    private val saveArticleViewModel: ArticleViewModel by viewModel()
    private val articleAdapter = ArticleAdapter(ArticleAdapter.AdapterType.FAVORITE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        observeData()
    }

    private fun setupViews() {
        setupToolbar()
        setupRecyclerView()
    }

    @Suppress("DEPRECATION")
    private fun setupToolbar() {
        binding.toolbar.setupToolbar(
            title = getString(R.string.save_article),
            showBackButton = true,
            backAction = { onBackPressed() }
        )
    }

    private fun setupRecyclerView() {
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(this@SaveArticleActivity)
            adapter = articleAdapter
        }
    }

    private fun observeData() {
        saveArticleViewModel.getAllFavoriteArticles().observe(this) { articles ->
            articles?.let {
                articleAdapter.setFavoriteArticles(it)
            }
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivitySaveArticleBinding =
        ActivitySaveArticleBinding.inflate(layoutInflater)
}

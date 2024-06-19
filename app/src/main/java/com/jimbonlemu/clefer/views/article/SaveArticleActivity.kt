package com.jimbonlemu.clefer.views.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivitySaveArticleBinding
import com.jimbonlemu.clefer.utils.Prefs
import com.jimbonlemu.clefer.views.article.adapter.ArticleAdapter
import com.jimbonlemu.clefer.views.article.viewmodels.ArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SaveArticleActivity : CoreActivity<ActivitySaveArticleBinding>() {
    private val saveArticleViewModel: ArticleViewModel by viewModel()
    private val articleAdapter = ArticleAdapter(ArticleAdapter.AdapterType.FAVORITE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
        setupToolbar(binding.mToolbar)
        observeData()
    }

    private fun setupRecyclerView() {
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(this@SaveArticleActivity)
            adapter = articleAdapter
        }
    }

    private fun observeData() {
        saveArticleViewModel.getAllFavoriteArticles(Prefs.getUserId).observe(this) { articles ->
            articles?.let {
                if (it.isEmpty()) {
                    isEmptyLayoutEnable(true)
                } else {
                    isEmptyLayoutEnable(false)
                    articleAdapter.setFavoriteArticles(it)
                }
            }
        }
    }

    private fun isEmptyLayoutEnable(isEnable: Boolean) {
        binding.apply {
            emptyLayoutFavouriteArticle.apply {
                if (isEnable) {
                    root.visibility = View.VISIBLE
                    tvEmptyTitle.text = getString(R.string.title_empty_favorite_articles)
                    rvItems.visibility = View.GONE
                } else {
                    root.visibility = View.GONE
                    rvItems.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivitySaveArticleBinding =
        ActivitySaveArticleBinding.inflate(layoutInflater)
}

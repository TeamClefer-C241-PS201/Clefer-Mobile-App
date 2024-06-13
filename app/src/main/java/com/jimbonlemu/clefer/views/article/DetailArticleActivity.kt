package com.jimbonlemu.clefer.views.article

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.databinding.ActivityDetailArticleBinding
import com.jimbonlemu.clefer.source.local.entity.FavoriteArticle
import com.jimbonlemu.clefer.views.article.viewmodels.ArticleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding
    private val detailArticleViewModel: ArticleViewModel by viewModel()
    private var isFavorite: Boolean = false
    private var articleId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up Toolbar
        binding.toolbar.setupToolbar(
            title = getString(R.string.detail_artikel_title),
            showBackButton = true,
            backAction = { onBackPressedDispatcher.onBackPressed() }
        )
        val extraArticle = intent.getIntExtra(EXTRA_ARTICLE, 0)
        articleId = extraArticle
        detailArticleViewModel.getDetail(extraArticle)
        detailArticleViewModel.detail.observe(this) { article ->
            if (article != null) {
                binding.tvTitleDetail.text = article.articleTitle
                binding.tvDescDetail.text = article.articleDesc
                Glide.with(this@DetailArticleActivity)
                    .load(article.articleImg)
                    .into(binding.ivItemPhotoDetail)
                checkFavorite(articleId)
            }
        }
        binding.btnSaveBookmarks.setOnClickListener {
            if (isFavorite) {
                deleteFavorite(articleId)
            } else {
                addFavorite(articleId)
            }
        }
    }
    private fun addFavorite(id: Int) {
        val favoriteArticle = FavoriteArticle(
            id = id,
            articleTitle = binding.tvTitleDetail.text.toString(),
            articleDesc = binding.tvDescDetail.text.toString(),
            articleImg = detailArticleViewModel.detail.value?.articleImg ?: ""
        )
        detailArticleViewModel.insertFavoriteArticle(favoriteArticle)
        isFavorite = true
        updateFavoriteUI()
    }


    private fun deleteFavorite(id: Int) {
        detailArticleViewModel.deleteFavorite(id)
        isFavorite = false
        updateFavoriteUI()
    }

    private fun checkFavorite(id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            val count = detailArticleViewModel.checkFavoriteById(id)
            withContext(Dispatchers.Main) {
                isFavorite = count > 0
                updateFavoriteUI()
            }
        }
    }

    private fun updateFavoriteUI() {
        val iconRes = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        binding.btnSaveBookmarks.setImageResource(iconRes)
    }

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }
}
